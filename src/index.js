const express = require('express')
const { PrismaClient } = require('@prisma/client')

const prisma = new PrismaClient()
const app = express()

app.use(express.json())

app.get('/', async (req, res) => {
  res.json("HelLo mandu.in app")
})

app.get('/landmark', async (req, res) => {
  try {
    const data = await prisma.landmark.findMany()
    res.json({success: true, data})
    return;
  } catch (error) {
    res.json({success: false, message: error});
    return;
  }
})

app.get('/wisata', async (req, res) => {
  try {
    const data = await prisma.wisata.findMany()
    res.json({success: true, data})   
    return; 
  } catch (error) {
    res.json({success: false, message: error});
    return;
  }

  })

app.get('/transaksi', async (req, res) => { 
  try {
    const data = await prisma.transaksi.findMany()
    res.json({success: true, data})
    return;
  } catch (error) {
    res.json({success: false, message: error});
    return;
  } 
  })
   
app.get(`/landmark/:id`, async (req, res) => {
  const { id } = req.params
try {
  const getLandmark = await prisma.landmark.findUnique({
    where: { land_id: Number(id) },
  })

  if(getLandmark == null) {
    res.status(400).json('Kosong')
    return;
  }
    res.json(getLandmark)
    return;
} catch (error) {
  res.json({success: false, message: error});
   return;
}
}) 

app.get(`/landmark/:searchString/wisata`, async (req, res) => {
  const { searchString } = req.params

  try {
    const data = await prisma.transaksi.findMany({
      where:  { label: { contains: searchString } },
    })
  
    if(data == ""){
      res.status(400).json('Kosong')
      return;
    }
      res.json({data})
      return;
    
  } catch (error) {
    res.json({success: false, message: error});
    return;
  }
})

app.get(`/provinsi/:searchProv`, async (req, res) => {
  const { searchProv } = req.params

  try {
    const data = await prisma.wisata.findMany({
      where:  { provinsi: { contains: searchProv } },
  
    })
  
    if(data == ""){
      res.status(400).json('Kosong')
      return;
    }
      res.json({data})  
      return;
  } catch (error) {
    res.json({success: false, message: error});
    return;
  }
  
})

app.get(`/wisata/:id`, async (req, res) => {
  const { id } = req.params

  try {
    const getWisata = await prisma.wisata.findUnique({
  
      where: { place_id: Number(id) },
    })
  
    if(getWisata == null){
      res.status(400).json('Kosong')
      return;
    }
      res.json(getWisata)
      return;
  } catch (error) {
    res.json({success: false, message: error});
    return;
  }
})

const PORT = process.env.PORT || 8080

const server = app.listen(PORT, '0.0.0.0', () =>
  console.log(`
🚀 Server ready at: http://localhost:${PORT}
⭐️ See sample requests: http://pris.ly/e/ts/rest-express#3-using-the-rest-api`),
)

