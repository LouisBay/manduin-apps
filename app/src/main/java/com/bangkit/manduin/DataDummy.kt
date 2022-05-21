package com.bangkit.manduin

import com.bangkit.manduin.model.NewsModel

object DataDummy {
    fun generateDummyNews() : ArrayList<NewsModel> {

        val news = ArrayList<NewsModel>()

        news.add(
            NewsModel(
                "Cicipi Rawon Tanpa Kuah? Menu Ini Ada di Festival Nasi Goreng Hotel Santika Premiere ICE-BSD City" ,
                "Pernah rasakan rawon tanpa kuah? Anda bisa mencicipinya di Festival Nasi Goreng Hotel Santika Premiere ICE-BSD City." ,
                "https://cdn-2.tstatic.net/tribunnews/foto/bank/thumbnails2/nasgorice.jpg",
            )
        )
        news.add(
            NewsModel(
                "Mengenal Rowo Bayu Banyuwangi, Viral Karena Diduga Lokasi Asli KKN di Desa Penari" ,
                "Menteri BUMN Erick Thohir juga penasaran dengan lokasi KKN di Desa Penari yang kabarnya terletak di sebuah desa di Banyuwangi." ,
                "https://cdn-2.tstatic.net/tribunnews/foto/bank/thumbnails2/telaga-rowo-bayu-di-banyuwangi-jatim.jpg",
            )
        )
        news.add(
            NewsModel(
                "Subway Launching Menu Baru Seafood dengan Harga Terjangkau" ,
                "Menu baru berbahan dasar udang dan kepiting ini menjadi banyak permintaan pecinta Subway Indonesia." ,
                "https://cdn-2.tstatic.net/tribunnews/foto/bank/thumbnails2/menu-baru-subway.jpg",
            )
        )
        news.add(
            NewsModel(
                "Indahnya Keberagaman, Ini 6 Destinasi Wisata Religi Agama Buddha yang Populer di Indonesia" ,
                "Begitu kayaknya keberagaman, Indonesia memiliki beragam tempat ibadah yang bisa dijadikan destinasi wisata religi favorit." ,
                "https://cdn-2.tstatic.net/tribunnews/foto/bank/thumbnails2/vihara-ksitigarbha-bodhisattva-sumber-shutterstock.jpg",
            )
        )
        news.add(
            NewsModel(
                "Menikmati Kuliner Khas Cirebon Nasi Jamblang Hj Asnani, Ada Lebih dari 20-an Jenis Aneka Lauk Pauk" ,
                "Nasi Jamblang merupakan kuliner khas Cirebon yang disajikan di atas daun jati. Pengunjung dapat memilih sendiri lauk pauk yang akan disantapnya." ,
                "https://cdn-2.tstatic.net/tribunnews/foto/bank/thumbnails2/pawon-sego-jamblang-hj-asnani-cirebon_1.jpg",
            )
        )
        return news
    }
}