# Cloud Computing Path

Creating RESTful APIs and deploying to Google Cloud Platform by using App Engine for connection between android application and database. Using Cloud SQL for creating the database server.

## RESTful APIs
In making the RESTful APIs we use NodeJS with the Prisma Framework and Express for building an API server, and for responses using JSON format.
Explanation for each URL that can be used :

### List Landmarks
In this section there is a list of all landmarks that can be filtered by id or label. Response from each URL using JSON format.

**Base URL :**
> https://manduin-app.et.r.appspot.com/

**Path :**
> /landmark

**Method :**
> `GET`

**Show List All Landmarks**
> https://manduin-app.et.r.appspot.com/landmark

```json
{
    "success": true,
    "data": [
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
        },
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
    ]
}
```

<br>

**Show List Landmark filtering by land_id**
> https://manduin-app.et.r.appspot.com/landmark/:id

  **Required**
  > id = [int]

  **Example request**
  > https://manduin-app.et.r.appspot.com/landmark/2

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
> https://manduin-app.et.r.appspot.com/landmark/:label/wisata

  **Required**
  > label = [string]

  **Example request**
  > https://manduin-app.et.r.appspot.com/landmark/borobudur/wisata

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
    ]
}
```

<br>

### List Wisata
In this section there is a list of all wisata that can be filtered by id or province. Response from each URL using JSON format.

**Base URL :**
> https://manduin-app.et.r.appspot.com/

**Path :**
> /wisata

**Method :**
> `GET`

**Show List All Wisata**
> https://manduin-app.et.r.appspot.com/wisata

```json
{
    "success": true,
    "data": [
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
        },
        {
            "place_id": 2,
            "nama": "Kampung Pelangi",
            "city": "Semarang",
            "provinsi": "Jawa Tengah",
            "description": "Kampung pelangi atau dalam bahasa Inggris disebut Rainbow Village merupakan sebutan untuk daerah atau kampung yang rumah-rumah penduduknya dicat dengan warna-warni. Sebenarnya Kampung Pelangi pada awalnya merupakan daerah kumuh yang dikemudian secara kreatif diubah menjadi daerah yang menarik untuk dijadikan sebagai destinasi wisata, terutama bagi masyarakat yang senang atau sedang mencari spot yang menarik untuk mengabadikan momen.\\nBanyak daerah-daerah di Indonesia, terutama di jawa memliki tempat yang disebut Kampung pelangi, antara lain di Malang, Semarang, Jakarta, Bandung, dan kota-kota lainnya. Contoh kampung seperti ini yaitu, kampung warna-warni di Jodipan dan Tridi, Kota Malang, Jawa Timur.",
            "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/semarang/Kampung%20Pelangi.jpg",
            "lat": -6.98803,
            "lon": 110.4083,
            "category": "Taman Hiburan",
            "price": 3000,
            "rating": 43
        },
    ]
}
```

<br>

**Show List wisata filtering by land_id**
> https://manduin-app.et.r.appspot.com/wisata/:id

  **Required**
  > id = [int]

  **Example request**
  > https://manduin-app.et.r.appspot.com/wisata/3

```json
{
    "place_id": 3,
    "nama": "Taman Pandanaran",
    "city": "Semarang",
    "provinsi": "Jawa Tengah",
    "description": "Dalam sejarah yang tercatat, dulunya tempat ini merupakan kawasan SPBU yang tak begitu laku. Bekas SPBU tersebut dirombak total menjadi sebuah taman. Konon, luas tanah 600 meter persegi tersebut direnovasi menjadi taman dengan dana sekitar Rp1,8 M. Taman Pandanaran ini beralamatkan di Jalan Pandanaran, Mugassari, Semarang Selatan. Tepatnya, berada di pertigaan antara Jalan Pandanaran dan Jalan MH.Thamrin. Sayangnya, untuk mendapatkan parkir di sini cukup susah. Beberapa pengunjung bahkan harus memarkirkan kendaraannya di depan warung makan di sekitaran taman.",
    "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/semarang/Taman%20Pandanaran.JPG",
    "lat": -6.98711,
    "lon": 110.4172,
    "category": "Taman Hiburan",
    "price": 0,
    "rating": 44
}
```

<br>

**Show List Landmark search by label**
> https://manduin-app.et.r.appspot.com/provinsi/:searchProv

  **Required**
  > searchProv = [string]

  **Example request**
  > https://manduin-app.et.r.appspot.com/provinsi/yogya

```json
{
    "data": [
        {
            "place_id": 16,
            "nama": "Kebun Teh Nglinggo",
            "city": "Yogyakarta",
            "provinsi": "Daerah Istimewa Yogyakarta",
            "description": "Wisata Kebun Teh Nglinggo adalah satu-satunya kebun teh yang berada di Yogyakarta. Sebenarnya kebun teh ini merupakan tempat mata pencaharian penduduk di sekitar Pagerhajo. Kemudian warga mempunyai ide untuk menjadikannya sebagai tempat wisata. Dengan pesona alamnya Kebun Teh Nglinggo menjadi objek wisata yang cukup terkenal di Kulon Progo. Kebun Teh Nglinggo terletak di wilayah Perbukitan Menoreh. Lokasinya di Desa Wisata Nglinggo, Nglinggo Barat, Pagerharjo, Samigaluh, Kabupaten Kulon Progo, DIY. Wisata ini menawarkan objek wisata berupa hamparan perkebunan teh dengan luas sekitar 136 hektare di ketinggian 900-1000 mdpl . Di sana pengunjung dapat melihat para pemetik daun teh dan dapat pula ikut memanen daun teh. Jika kurang puas pengunjung juga dapat naik hingga Puncak Kendeng Gunung Kukusan, dan akan dimanjakan dengan pemandangan alam yang memikat mata juga udara sejuk pegunungan yang masih alami . Dari puncak tersebut pengunjung akan disajikan dengan pemandangan delapan gunung besar yang ada di Jogja dan Jawa Tengah . Pengunjung juga bisa menjelajahi Kebun Teh Nglinggo dengan jeep ataupun motor trail. Meskipun buka dari pukul 06.00-18.00 WIB, tetapi Kebun Teh Nglinggo dapat dijadikan objek wisata untuk berburu sunrise. Karena pengunjung dapat bermalam di home stay sekitar. Untuk harga tiket masuk objek wisata Kebun Teh Nglinggo sebesar Rp 5000 per orang.",
            "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/jogja/Kebun%20Teh%20Nglinggo.jpg",
            "lat": -7.6465,
            "lon": 110.1417,
            "category": "Cagar Alam",
            "price": 6000,
            "rating": 45
        },
        {
            "place_id": 17,
            "nama": "Grojogan Watu Purbo Bangunrejo",
            "city": "Yogyakarta",
            "provinsi": "Daerah Istimewa Yogyakarta",
            "description": "Objek wisata itu tak adalah Grojogan Watu Purbo yang berada di Bangunrejo, Merdikorejo, Kecamatan Tempel. Objek wisata itu sekitar setahun terakhir cukup populer di kalangan wisatawan karena memiliki pemandangan eksotis berupa air terjun yang memiliki enam tingkatan. Wisatawan yang datang rata-rata menjadikan air terjun itu sebagai latar untuk swafoto karena pemandangannya yang dinilai instagramable. Grojokan Watu Purbo ini tepatnya berlokasi di aliran Kali Krisak, yang merupakan jalur dari lahar dingin yang mengalir dari Gunung Merapi. Pemandangan kawasan ini eksotis karena dikepung pepohonan asri serta hamparan sawah. Munculnya air terjun atau grojogan ini berasal dari enam dam dengan ketinggian bervariasi tak lebih dari 10 meter.",
            "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/jogja/Grojogan%20Watu%20Purbo%20Bangunrejo.jpg",
            "lat": -7.63045,
            "lon": 110.3405,
            "category": "Taman Hiburan",
            "price": 10000,
            "rating": 45
        }
    ]
}
```

<br>

### List Transaksi
In this section there is a list of all transaksi that is the data transactions between wisatat data and landmark data. Response from each URL using JSON format.

**Base URL :**
> https://manduin-app.et.r.appspot.com/

**Path :**
> /transaksi

**Method :**
> `GET`

**Show List All Wisata**
> https://manduin-app.et.r.appspot.com/transaksi

```json
{
    "success": true,
    "data": [
        {
            "land_id": 1,
            "label": "lawangsewu",
            "place_id": 1,
            "nama_wisata": "Tugu Muda Semarang",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/semarang/Tugu%20Muda%20Semarang.jpg",
            "lat": -6.98377,
            "lon": 110.409
        },
        {
            "land_id": 1,
            "label": "lawangsewu",
            "place_id": 2,
            "nama_wisata": "Kampung Pelangi",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/semarang/Kampung%20Pelangi.jpg",
            "lat": -6.98803,
            "lon": 110.4083
        },
        {
            "land_id": 1,
            "label": "lawangsewu",
            "place_id": 3,
            "nama_wisata": "Taman Pandanaran",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/semarang/Taman%20Pandanaran.JPG",
            "lat": -6.98711,
            "lon": 110.4172
        },
        {
            "land_id": 2,
            "label": "majt",
            "place_id": 3,
            "nama_wisata": "Taman Pandanaran",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/semarang/Taman%20Pandanaran.JPG",
            "lat": -6.98711,
            "lon": 110.4172
        },
        {
            "land_id": 2,
            "label": "majt",
            "place_id": 5,
            "nama_wisata": "Indonesia Kaya Park",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/semarang/Indonesia%20Kaya%20Park.jpg",
            "lat": -6.99129,
            "lon": 110.4201
        },
        {
            "land_id": 2,
            "label": "majt",
            "place_id": 7,
            "nama_wisata": "Semarang Chinatown",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/semarang/Semarang%20Chinatown.jpg",
            "lat": -6.9741,
            "lon": 110.4252
        }
    ]
}
```
