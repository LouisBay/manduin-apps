# Cloud Computing Path

Creating RESTful APIs and deploying to Google Cloud Platform by using Compute Engine for connection between android application and database. Using Cloud SQL for creating the database server.

## RESTful APIs
In making the RESTful APIs we use NodeJS with the Prisma Framework and Express for building an API server, and for responses using JSON format.
Explanation for each URL that can be used :

### List Landmarks
In this section there is a list of all landmarks that can be filtered by id or label. Response from each URL using JSON format.

**Base URL :**
> http://34.101.118.46:3000/

**Path :**
> /landmark

**Method :**
> `GET`

**Show List All Landmarks**
> http://34.101.118.46:3000/landmark

```json
{
            "land_id": 1,
            "label": "lawangsewu",
            "nama": "Lawang Sewu",
            "description": "Lawang Sewu (\"Seribu Pintu\") (bahasa Jawa: ꦭꦮꦁ​ꦱꦺꦮꦸ, translit. Lawang Sèwu) adalah landmark di Semarang, Jawa Tengah, Indonesia, yang dibangun sebagai kantor pusat Perusahaan Kereta Api Hindia Belanda. Bangunan era kolonial terkenal sebagai rumah berhantu dan lokasi syuting, meskipun pemerintah kota Semarang telah berusaha mengubah citra itu.",
            "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/landmark_images/lawang_sewu.jpg",
            "lat": -6.98389,
            "lon": 110.4104,
            "category": "Budaya",
            "city": "Semarang",
            "price": 10000,
            "rating": 46
}
```

<br>

**Show List Landmark filtering by land_id**
> http://34.101.118.46:3000/landmark/:id

  **Required**
  > id atau land_id = [int]

  **Example request**
  > http://34.101.118.46:3000/landmark/2

```json
{
    "land_id": 2,
    "label": "majt",
    "nama": "Masjid Agung Jawa Tengah",
    "description": "Masjid Agung Jawa Tengah  adalah masjid yang terletak di Semarang, provinsi Jawa Tengah, Indonesia. Masjid ini mulai dibangun sejak tahun 2001 hingga selesai secara keseluruhan pada tahun 2006. Masjid ini berdiri di atas lahan 10 hektare. Masjid Agung diresmikan oleh Presiden Indonesia Susilo Bambang Yudhoyono pada tanggal 14 November 2006. Masjid Agung Jawa Tengah (MAJT) merupakan masjid provinsi bagi provinsi Jawa Tengah.",
    "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/landmark_images/majt.jpg",
    "lat": -6.9834,
    "lon": 110.4451,
    "category": "Tempat Ibadah",
    "city": "Semarang",
    "price": 0,
    "rating": 45
}
```

<br>

**Show List Landmark search by label**
> http://34.101.118.46:3000/landmark/:label/wisata

  **Required**
  > label = [string]

  **Example request**
  > http://34.101.118.46:3000/landmark/borobudur/wisata

```json
{
    "data": [
        {
            "land_id": 3,
            "label": "borobudur",
            "place_id": 16,
            "nama_wisata": "Kebun Teh Nglinggo",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/jogja/Kebun%20Teh%20Nglinggo.jpg",
            "lat": -7.6465,
            "lon": 110.1417
        },
        {
            "land_id": 3,
            "label": "borobudur",
            "place_id": 17,
            "nama_wisata": "Grojogan Watu Purbo Bangunrejo",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/jogja/Grojogan%20Watu%20Purbo%20Bangunrejo.jpg",
            "lat": -7.63045,
            "lon": 110.3405
        }
}
```

<br>

### List Wisata
In this section there is a list of all wisata that can be filtered by id or province. Response from each URL using JSON format.

**Base URL :**
> http://34.101.118.46:3000/

**Path :**
> /wisata

**Method :**
> `GET`

**Show List All Wisata**
> http://34.101.118.46:3000/wisata

```json
{
            "place_id": 1,
            "nama": "Tugu Muda Semarang",
            "city": "Semarang",
            "provinsi": "Jawa Tengah",
            "description": "Tugu Muda (\"Monumen Pemuda\" Indonesia) adalah sebuah monumen batu di Semarang, Jawa memperingati perjuangan kemerdekaan oleh pemuda Indonesia. Itu didedikasikan oleh Presiden Sukarno pada 20 Mei 1953 untuk memperingati pertempuran lima hari terus menerus antara pemuda Semarang dan batalyon Jepang yang dipimpin oleh Mayor Kido dari 14-19 Oktober 1945. Pasukan Jepang mengusir Belanda dari Indonesia sebagai \"kakak laki-laki Asia\"; namun, Jepang lebih kejam terhadap pembangkang daripada rekan-rekan Belanda mereka. Tugu batu tersebut terdiri dari sebuah pondasi, badan dan kepala. Satu sisi tugu dibuat dalam relief, dengan kolam hias dan taman yang mengelilinginya.",
            "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/semarang/Tugu%20Muda%20Semarang.jpg",
            "lat": -6.98377,
            "lon": 110.409,
            "category": "Budaya",
            "price": 0,
            "rating": 47
}
```
