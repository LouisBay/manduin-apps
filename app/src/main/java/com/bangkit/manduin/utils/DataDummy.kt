package com.bangkit.manduin.utils

import com.bangkit.manduin.R
import com.bangkit.manduin.model.FavoriteModel
import com.bangkit.manduin.model.ProvinceModel
import com.bangkit.manduin.model.ReviewModel

object DataDummy {

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
                "DKI Jakarta" ,
                "https://3.bp.blogspot.com/-7b4VZpjgzYY/Vtg760Lzl0I/AAAAAAAAAcQ/zR2WA1flHQE/s320/12.jpg" ,
            )
        )
        province.add(
            ProvinceModel(
                "Jawa Barat" ,
                "https://4.bp.blogspot.com/-0WDb4S7MAJk/Vtg-XO97dzI/AAAAAAAAAcc/wvn5vJw4q08/s320/13.jpg" ,
            )
        )
        province.add(
            ProvinceModel(
                "Jawa Tengah" ,
                "https://4.bp.blogspot.com/-9cTRtgqoLFs/Vtg_nBNs6yI/AAAAAAAAAco/--c_jgDGJD8/s320/14.jpg" ,
            )
        )
        province.add(
            ProvinceModel(
                "Daerah Istimewa Yogyakarta" ,
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