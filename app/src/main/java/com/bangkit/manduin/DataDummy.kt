package com.bangkit.manduin

import com.bangkit.manduin.model.FavoriteModel
import com.bangkit.manduin.model.NewsModel
import com.bangkit.manduin.model.ProvinceModel
import com.bangkit.manduin.model.ReviewModel

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

    fun generateDummyProvince() : ArrayList<ProvinceModel> {
        val province = ArrayList<ProvinceModel>()
        province.add(
            ProvinceModel(
                "Banten" ,
                "https://4.bp.blogspot.com/-I2CdF3zBl64/Vtg65QUXa1I/AAAAAAAAAcE/wkhf7Ec2FNo/s320/11.jpg" ,
            )
        )
        province.add(
            ProvinceModel(
                "Special Capital Region of Jakarta" ,
                "https://3.bp.blogspot.com/-7b4VZpjgzYY/Vtg760Lzl0I/AAAAAAAAAcQ/zR2WA1flHQE/s320/12.jpg" ,
            )
        )
        province.add(
            ProvinceModel(
                "West Java" ,
                "https://4.bp.blogspot.com/-0WDb4S7MAJk/Vtg-XO97dzI/AAAAAAAAAcc/wvn5vJw4q08/s320/13.jpg" ,
            )
        )
        province.add(
            ProvinceModel(
                "Central Java" ,
                "https://4.bp.blogspot.com/-9cTRtgqoLFs/Vtg_nBNs6yI/AAAAAAAAAco/--c_jgDGJD8/s320/14.jpg" ,
            )
        )
        province.add(
            ProvinceModel(
                "Special Region of Yogyakarta" ,
                "https://1.bp.blogspot.com/-hWy5xW9JIG0/VthAcu4vKXI/AAAAAAAAAc0/jSZLyHgL1kM/s320/15.jpg" ,
            )
        )
        province.add(
            ProvinceModel(
                "East Java" ,
                "https://3.bp.blogspot.com/-gsUvtPTpQ88/VthAq-daQPI/AAAAAAAAAc4/Q6Fl6dXHWVA/s320/16.jpg" ,
            )
        )
        return province
    }

    fun generateDummyFavorite() : ArrayList<FavoriteModel> {
        val favorite = ArrayList<FavoriteModel>()
        favorite.add(
            FavoriteModel(
                "Prambanan Temple" ,
                "Special Region of Yogyakarta" ,
                "https://lh3.googleusercontent.com/HQ-RkLoVOQxe27d-_4wiTMxFn6XGTJag4-rElmJlmkSpAHvx53LMGrJjV48rSRhx8w=s1200" ,
            )
        )

        favorite.add(
            FavoriteModel(
                "Borobudur Temple" ,
                "Magelang, Central Java" ,
                "https://awsimages.detik.net.id/community/media/visual/2020/03/16/e038d51f-daaa-40e4-83d6-e404095126ff_169.jpeg?w=700&q=90" ,
            )
        )

        favorite.add(
            FavoriteModel(
                "Lawang Sewu" ,
                "Semarang, Central Java" ,
                "https://heritage.kai.id/media/cover%2013.jpg?1505617606123" ,
            )
        )
        return favorite
    }

    fun generateDummyReview() : ArrayList<ReviewModel> {
        val review = ArrayList<ReviewModel>()
        review.add(
            ReviewModel(
                "User" ,
                "Awesome" ,
                "22/04/2022" ,
                R.drawable.profile_ava
            )
        )
        review.add(
            ReviewModel(
                "User" ,
                "Awesome" ,
                "22/04/2022" ,
                R.drawable.profile_ava
            )
        )
        review.add(
            ReviewModel(
                "User" ,
                "Awesome" ,
                "22/04/2022" ,
                R.drawable.profile_ava
            )
        )

        return review
    }
}