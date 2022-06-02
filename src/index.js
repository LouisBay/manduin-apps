const express = require('express')
const { PrismaClient } = require('@prisma/client')

const prisma = new PrismaClient()
const app = express()

app.use(express.json())

app.get('/landmark', async (req, res) => {
    const data = await prisma.landmark.findMany()
    res.json({success: true, data})
  })

app.get('/wisata', async (req, res) => {
    const data = await prisma.wisata.findMany()
    res.json({success: true, data})
  })

app.get('/transaksi', async (req, res) => {  
    const data = await prisma.transaksi.findMany()
    res.json({success: true, data})
  })
   
app.get(`/landmark/:id`, async (req, res) => {
  const { id } = req.params

  const getLandmark = await prisma.landmark.findUnique({
    where: { land_id: Number(id) },
  })

  if(getLandmark == null)
    res.status(400).json('Kosong')
  else
    res.json(getLandmark)
})

app.get(`/landmark/:searchString/wisata`, async (req, res) => {
  const { searchString } = req.params

  const data = await prisma.transaksi.findMany({
    where:  { label: { contains: searchString } },
  })

  if(data == "")
    res.status(400).json('Kosong')
  else
    res.json({data})
})

app.get(`/provinsi/:searchProv`, async (req, res) => {
  const { searchProv } = req.params

  const data = await prisma.wisata.findMany({
    where:  { provinsi: { contains: searchProv } },

  })

  if(data == "")
    res.status(400).json('Kosong')
  else
    res.json({data})
})

app.get(`/wisata/:id`, async (req, res) => {
  const { id } = req.params

  const getWisata = await prisma.wisata.findUnique({

    where: { place_id: Number(id) },
  })

  if(getWisata == null)
    res.status(400).json('Kosong')
  else
    res.json(getWisata)
})

const server = app.listen(3000, '0.0.0.0', () =>
  console.log(`
🚀 Server ready at: http://localhost:3000
⭐️ See sample requests: http://pris.ly/e/ts/rest-express#3-using-the-rest-api`),
)

