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
        },
        {
            "land_id": 3,
            "label": "borobudur",
            "nama": "Candi Borobudur",
            "description": "Borobudur (bahasa Jawa: ꦕꦤ꧀ꦝꦶ​ꦧꦫꦧꦸꦝꦸꦂ, translit. Candhi Barabudhur) adalah sebuah candi Buddha yang terletak di Borobudur, Magelang, Jawa Tengah, Indonesia. Candi ini terletak kurang lebih 100 km di sebelah barat daya Semarang, 86 km di sebelah barat Surakarta, dan 40 km di sebelah barat laut Yogyakarta. Candi berbentuk stupa ini didirikan oleh para penganut agama Buddha Mahayana sekitar tahun 800-an Masehi pada masa pemerintahan wangsa Syailendra. Borobudur adalah candi atau kuil Buddha terbesar di dunia, sekaligus salah satu monumen Buddha terbesar di dunia.\\nMonumen ini terdiri atas enam teras berbentuk bujur sangkar yang di atasnya terdapat tiga pelataran melingkar, pada dindingnya dihiasi dengan 2.672 panel relief dan aslinya terdapat 504 arca Buddha. Borobudur memiliki koleksi relief Buddha terlengkap dan terbanyak di dunia. Stupa utama terbesar teletak di tengah sekaligus memahkotai bangunan ini, dikelilingi oleh tiga barisan melingkar 72 stupa berlubang yang di dalamnya terdapat arca Buddha tengah duduk bersila dalam posisi teratai sempurna dengan mudra (sikap tangan) Dharmachakra mudra (memutar roda dharma).\\nMonumen ini merupakan model alam semesta dan dibangun sebagai tempat suci untuk memuliakan Buddha sekaligus berfungsi sebagai tempat ziarah untuk menuntun umat manusia beralih dari alam nafsu duniawi menuju pencerahan dan kebijaksanaan sesuai ajaran Buddha. Para peziarah masuk melalui sisi timur dan memulai ritual di dasar candi dengan berjalan melingkari bangunan suci ini searah jarum jam, sambil terus naik ke undakan berikutnya melalui tiga tingkatan ranah dalam kosmologi Buddha. Ketiga tingkatan itu adalah Kāmadhātu (ranah hawa nafsu), Rupadhatu (ranah berwujud), dan Arupadhatu (ranah tak berwujud). Dalam perjalanannya para peziarah berjalan melalui serangkaian lorong dan tangga dengan menyaksikan tak kurang dari 1.460 panel relief indah yang terukir pada dinding dan pagar langkan.\\nMenurut bukti-bukti sejarah, Borobudur ditinggalkan pada abad ke-14 seiring melemahnya pengaruh kerajaan Hindu dan Buddha di Jawa serta mulai masuknya pengaruh Islam. Dunia mulai menyadari keberadaan bangunan ini sejak ditemukan 1814 oleh Sir Thomas Stamford Raffles, yang saat itu menjabat sebagai Gubernur Jenderal Inggris atas Jawa. Sejak saat itu Borobudur telah mengalami serangkaian upaya penyelamatan dan pemugaran (perbaikan kembali). Proyek pemugaran terbesar digelar pada kurun waktu 1975 hingga 1982 atas upaya Pemerintah Republik Indonesia dan UNESCO, kemudian situs bersejarah ini masuk dalam daftar Situs Warisan Dunia.Borobudur kini masih digunakan sebagai tempat ziarah keagamaan; tiap tahun umat Buddha yang datang dari seluruh Indonesia dan mancanegara berkumpul di Borobudur untuk memperingati Trisuci Waisak. Dalam dunia pariwisata, Borobudur adalah objek wisata tunggal di Indonesia yang paling banyak dikunjungi wisatawan.",
            "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/landmark_images/borobudur.jpg",
            "lat": -7.60785,
            "lon": 110.2037,
            "category": "Budaya",
            "city": "Magelang",
            "price": 50000,
            "rating": 47
        },
        {
            "land_id": 4,
            "label": "tugujogja",
            "nama": "Tugu Pal Putih Jogja",
            "description": "Tugu Yogyakarta (Jawa: , Tugu Ngayogyakarta) adalah sebuah landmark bersejarah yang penting di kota Yogyakarta, Indonesia. Tugu berarti tugu, yang biasanya dibangun sebagai simbol suatu kawasan yang mengkonseptualisasikan ciri-ciri kawasan tersebut. Karena latar belakang sejarahnya, Tugu Yogyakarta telah menjadi ikon sejarah kota. Tugu Yogyakarta terletak tepat di tengah persimpangan antara Jalan Mangkubumi, Jalan Sudirman, Jalan A.M Sangaji, dan Jalan Dipenogoro kota.",
            "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/landmark_images/tugu_jogja.jpg",
            "lat": -7.78276,
            "lon": 110.3671,
            "category": "Taman Hiburan",
            "city": "Yogyakarta",
            "price": 0,
            "rating": 47
        },
        {
            "land_id": 5,
            "label": "monjali",
            "nama": "Monumen Yogya Kembali",
            "description": "Museum Monumen Yogya Kembali (bahasa Jawa: ꦩꦺꦴꦤꦸꦩꦺꦤ꧀​ꦪꦺꦴꦒꦾ​ꦏꦼꦩ꧀ꦧꦭꦶ, translit. Monumèn Yogya Kembali) biasa dikenal sebagai Monumen Jogja Kembali disingkat Monjali adalah sebuah museum sejarah perjuangan kemerdekaan Indonesia yang ada di Daerah Istimewa Yogyakarta dan dikelola oleh Kementerian Pariwisata dan Ekonomi Kreatif. Museum yang berada di bagian utara kota ini banyak dikunjungi oleh para pelajar dalam acara darmawisata.\\n\\nMuseum monumen dengan bentuk kerucut ini terdiri dari 3 lantai dan dilengkapi dengan ruang perpustakaan serta ruang serbaguna. Pada rana pintu masuk dituliskan sejumlah 422 nama pahlawan yang gugur di daerah Wehrkreise III (RIS) antara tanggal 19 Desember 1948 sampai dengan 29 Juni 1949. Dalam 4 ruang museum di lantai 1 terdapat benda-benda koleksi: relief, replika, foto, dokumen, heraldika, berbagai jenis senjata, bentuk evokatif dapur umum dalam suasana perang kemerdekaan 1945-1949. Tandu dan dokar (kereta kuda) yang pernah dipergunakan oleh Panglima Besar Jenderal Soedirman juga disimpan di sini (di ruang museum nomor 2). Monumen Yogya Kembali beralamat di Jl. Ring Road Utara, Kabupaten Sleman, Daerah Istimewa Yogyakarta.",
            "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/landmark_images/monjali.jpg",
            "lat": -7.74965,
            "lon": 110.3696,
            "category": "Budaya",
            "city": "Yogyakarta",
            "price": 15000,
            "rating": 45
        },
        {
            "land_id": 6,
            "label": "prambanan",
            "nama": "Candi Prambanan",
            "description": "Candi Prambanan atau Candi Roro Jonggrang (bahasa Jawa: ꦕꦤ꧀ꦝꦶ​ꦥꦿꦩ꧀ꦧꦤꦤ꧀, translit. Candhi Prambanan) adalah kompleks candi Hindu terbesar di Indonesia yang dibangun pada abad ke-9 masehi. Candi ini dipersembahkan untuk Trimurti, tiga dewa utama Hindu yaitu Brahma sebagai dewa pencipta, Wisnu sebagai dewa pemelihara, dan Siwa sebagai dewa pemusnah. Berdasarkan prasasti Siwagrha nama asli kompleks candi ini adalah Siwagrha (bahasa Sanskerta yang bermakna 'Rumah Siwa'), dan memang di garbagriha (ruang utama) candi ini bersemayam arca Siwa Mahadewa setinggi tiga meter yang menujukkan bahwa di candi ini dewa Siwa lebih diutamakan.\\nKompleks percandian Candi Prambanan secara keseluruhan berada di wilayah provinsi Daerah Istimewa Yogyakarta, namun pintu administrasinya berada di Daerah Istimewa Surakarta (sekarang bagian Provinsi Jawa Tengah), hal ini yang membuat Candi Prambanan terletak di 2 tempat yakni di Desa Bokoharjo, Kecamatan Prambanan, Kabupaten Sleman, dan di Desa Tlogo, Kecamatan Prambanan, Kabupaten Klaten, atau kurang lebih 17 kilometer timur laut Yogyakarta, 50 kilometer barat daya Surakarta dan 120 kilometer selatan Semarang, persis di perbatasan antara Daerah Istimewa Yogyakarta dan Daerah Istimewa Surakarta.Candi ini adalah termasuk Situs Warisan Dunia UNESCO, candi Hindu terbesar di Indonesia, sekaligus salah satu candi terindah di Asia Tenggara. Arsitektur bangunan ini berbentuk tinggi dan ramping sesuai dengan arsitektur Hindu pada umumnya dengan candi Siwa sebagai candi utama memiliki ketinggian mencapai 47 meter menjulang di tengah kompleks gugusan candi-candi yang lebih kecil. Sebagai salah satu candi termegah di Asia Tenggara, candi Prambanan menjadi daya tarik kunjungan wisatawan dari seluruh dunia.Menurut prasasti Siwagrha, candi ini mulai dibangun pada sekitar tahun 850 masehi oleh Rakai Pikatan, dan terus dikembangkan dan diperluas oleh Balitung Maha Sambu, pada masa kerajaan Medang Mataram.",
            "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/landmark_images/prambanan.jpg",
            "lat": -7.75197,
            "lon": 110.4914,
            "category": "Budaya",
            "city": "Yogyakarta",
            "price": 50000,
            "rating": 47
        }
    ]
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
        },
        {
            "land_id": 3,
            "label": "borobudur",
            "place_id": 18,
            "nama_wisata": "Desa Wisata Kelor",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/jogja/Desa%20Wisata%20Kelor.jpg",
            "lat": -7.64,
            "lon": 110.3621
        },
        {
            "land_id": 3,
            "label": "borobudur",
            "place_id": 19,
            "nama_wisata": "Desa Wisata Pulesari",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/jogja/Desa%20Wisata%20Pulesari.webp",
            "lat": -7.62499,
            "lon": 110.372
        },
        {
            "land_id": 3,
            "label": "borobudur",
            "place_id": 20,
            "nama_wisata": "Taman Sungai Mudal",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/jogja/Taman%20Sungai%20Mudal.jpeg",
            "lat": -7.76213,
            "lon": 110.1162
        },
        {
            "land_id": 3,
            "label": "borobudur",
            "place_id": 21,
            "nama_wisata": "Air Terjun Kedung Pedut",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/jogja/Air%20Terjun%20Kedung%20Pedut.jpg",
            "lat": -7.76905,
            "lon": 110.1209
        },
        {
            "land_id": 3,
            "label": "borobudur",
            "place_id": 22,
            "nama_wisata": "Jogja Exotarium",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/jogja/Jogja%20Exotarium.jpg",
            "lat": -7.72798,
            "lon": 110.3585
        },
        {
            "land_id": 3,
            "label": "borobudur",
            "place_id": 23,
            "nama_wisata": "Desa Wisata Gamplong",
            "img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/jogja/Desa%20Wisata%20Gamplong.jpg",
            "lat": -7.80898,
            "lon": 110.2352
        }
    ]
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
}
```

<br>

**Show List wisata filtering by land_id**
> http://34.101.118.46:3000/wisata/:id

  **Required**
  > id atau place_id = [int]

  **Example request**
  > http://34.101.118.46:3000/wisata/3

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
> http://34.101.118.46:3000/provinsi/:searchProv

  **Required**
  > searchProv = [string]

  **Example request**
  > http://34.101.118.46:3000/provinsi/yogya

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
        },
        {
            "place_id": 18,
            "nama": "Desa Wisata Kelor",
            "city": "Yogyakarta",
            "provinsi": "Daerah Istimewa Yogyakarta",
            "description": "Desa wisata Kelor merupakan salah satu desa yang ada di kabupaten Sleman yang saat ini sedang dikembangkan menjadi desa wisata. Desa wisata Kelor ini menawarkan suasana pedesaan yang penduduknya bermata pencaharian sebagai petani, peternak, dan budidaya jamur. Banyak tanaman hijau yang membentang luas dengan pemandangan alam yang asri. Desa wisata Kelor ini memiliki masyarakat yang ramah dan memiliki lingkungan desa yang sangat nyaman. Desa Kelor ini juga memiliki sungai, dan air di sungai tersebut masih jernih dan indah.",
            "Img_Url": "https://storage.googleapis.com/mandu-in-bucket/img/jogja/Desa%20Wisata%20Kelor.jpg",
            "lat": -7.64,
            "lon": 110.3621,
            "category": "Taman Hiburan",
            "price": 0,
            "rating": 44
        }
    ]
}
```

<br>

### List Transaksi
In this section there is a list of all transaksi that is the data transactions between wisatat data and landmark data. Response from each URL using JSON format.

**Base URL :**
> http://34.101.118.46:3000/

**Path :**
> /transaksi

**Method :**
> `GET`

**Show List All Wisata**
> http://34.101.118.46:3000/transaksi

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
