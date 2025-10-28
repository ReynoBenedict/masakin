package com.example.masakin.data.repository

import com.example.masakin.data.model.Post
import java.util.concurrent.TimeUnit

interface CommunityRepository {
    suspend fun getRecommended(): List<Post>
    suspend fun getRecent(): List<Post>
}

class FakeCommunityRepository : CommunityRepository {
    override suspend fun getRecommended(): List<Post> = demoPosts.shuffled()

    // Urut terbaru berdasar hasil parsing string time ("4m", "1h", "1d")
    override suspend fun getRecent(): List<Post> =
        demoPosts.sortedByDescending { it.sortKeyMillis() }
}

private fun Post.sortKeyMillis(): Long {
    val now = System.currentTimeMillis()
    // contoh time: "4m", "1h", "1d", dengan/spasi.
    val match = Regex("""(\d+)\s*([mhdMHD])""").find(time) ?: return 0L
    val n = match.groupValues[1].toLong()
    val unit = match.groupValues[2].lowercase() // m/h/d
    val delta = when (unit) {
        "m" -> TimeUnit.MINUTES.toMillis(n)
        "h" -> TimeUnit.HOURS.toMillis(n)
        "d" -> TimeUnit.DAYS.toMillis(n)
        else -> 0L
    }
    return now - delta
}

private val demoPosts = listOf(
    Post(
        id = "1",
        userName = "Jofi Salim",
        userHandle = "jofisalim23",
        userAvatarUrl = "https://i.pravatar.cc/150?img=1",
        time = "1h",
        content = "Tidak ada yang lebih memuaskan selain ayam geprek sambal matah…",
        imageUrl = "https://img-global.cpcdn.com/recipes/4f29e3debf3bd281/1502x1064cq70/ayam-geprek-sambal-matah-foto-resep-utama.jpg",
        likes = 17, comments = 8, shares = 4
    ),
    Post(
        id = "2",
        userName = "Kartini",
        userHandle = "kartini23",
        userAvatarUrl = "https://i.pravatar.cc/150?img=2",
        time = "30m",
        content = "Seperti ikan asin hangat di pagi hari! #RendangLovers #KulinerNusantara",
        imageUrl = "https://tse1.mm.bing.net/th/id/OIP.iqF3ki6asqHrS1-JvWPerwHaEX?pid=Api&P=0&h=180",
        likes = 42, comments = 11, shares = 7
    ),
    Post(
        id = "3",
        userName = "Antony",
        userHandle = "antony_id",
        userAvatarUrl = "https://asset.kompas.com/crops/gp4FPvFcCCOYS5DMcpjHD5xyFac=/28x0:1000x648/750x500/data/photo/2018/01/15/2417678426.jpg",
        time = "2h",
        content = "Minuman herbal kembali dengan topping jahe yang bikin hangat.",
        imageUrl = null,
        likes = 8, comments = 3, shares = 1
    ),
    Post(
        id = "4",
        userName = "Lamine Yamal",
        userHandle = "yamal10",
        userAvatarUrl = "https://i.pravatar.cc/150?img=4",
        time = "4m",
        content = "Klepon isi lumer, kelapa parutnya wangi. Wajib coba!",
        imageUrl = "https://file.fin.co.id/uploads/fin-lifestyle/images/2025/01/07/cara-membuat-klepon-pandan-sensasi-lumer-di-mulut-005334.jpg",
        likes = 91, comments = 25, shares = 10
    ),
    Post(
        id = "5",
        userName = "Syahroni",
        userHandle = "syahroni",
        userAvatarUrl = "https://i.pravatar.cc/150?img=5",
        time = "15m",
        content = "Sate maranggi asap tipis, daging empuk, sambal kecap pedas manis. #StreetFood",
        imageUrl = "https://choco.id/wp-content/uploads/2024/04/Sate-Maranggi1.jpg",
        likes = 65, comments = 14, shares = 9
    ),
    Post(
        id = "6",
        userName = "Almas",
        userHandle = "muralchick",
        userAvatarUrl = "https://tse3.mm.bing.net/th/id/OIP.iVyAespyKBVq-m0RA-A0HAHaD5?pid=Api&P=0&h=180",
        time = "3h",
        content = "Kolak pisang pas hujan. Manisnya pas, kuah santannya wangi daun pandan.",
        imageUrl = null,
        likes = 23, comments = 5, shares = 2
    ),
    Post(
        id = "7",
        userName = "Mudryk",
        userHandle = "cedargold",
        userAvatarUrl = "https://i.pravatar.cc/150?img=7",
        time = "50m",
        content = "Ayam bakar madu dengan sambal bawang—perpaduan manis pedas bikin nagih.",
        imageUrl = "https://tse1.mm.bing.net/th/id/OIP.uK0RGwAG7bN5CWPbmpefLAHaFP?pid=Api&P=0&h=180",
        likes = 33, comments = 6, shares = 4
    ),
    Post(
        id = "8",
        userName = "Sarweni",
        userHandle = "petotonsu",
        userAvatarUrl = "https://i.pravatar.cc/150?img=8",
        time = "6h",
        content = "Dendeng balado renyah gurih pedas, lauk wajib nasi panas!",
        imageUrl = "https://tse3.mm.bing.net/th/id/OIP.wDskN6wZjfKby9nZfZgO6QHaE8?pid=Api&P=0&h=180",
        likes = 12, comments = 4, shares = 1
    ),
    Post(
        id = "9",
        userName = "Codeblu",
        userHandle = "codeblu",
        userAvatarUrl = "https://i.pravatar.cc/150?img=9",
        time = "10m",
        content = "Sambal ijo segar, cocok banget buat rendang dan ayam pop. #KulinerPadang",
        imageUrl = "https://tse4.mm.bing.net/th/id/OIP.F9kwB1tHDbkASpkJ9LML8gHaFc?pid=Api&P=0&h=180",
        likes = 120, comments = 39, shares = 21
    ),
    Post(
        id = "10",
        userName = "Nex Carlos",
        userHandle = "next.carlos",
        userAvatarUrl = "https://i.pravatar.cc/150?img=10",
        time = "1d",
        content = "Burger lokal dengan bumbu rahasia—rotinya lembut, daging juicy!",
        imageUrl = "https://tse2.mm.bing.net/th/id/OIP.L_N3dUELFUk5uNvv2SQw1AHaEj?pid=Api&P=0&h=180",
        likes = 77, comments = 18, shares = 12
    ),
    Post(
        id = "11",
        userName = "Raisa",
        userHandle = "raisacooks",
        userAvatarUrl = "https://i.pravatar.cc/150?img=11",
        time = "8m",
        content = "Mie goreng tek-tek versi rumahan, pakai telur orak-arik dan sawi.",
        imageUrl = "https://tse2.mm.bing.net/th/id/OIP.L_N3dUELFUk5uNvv2SQw1AHaEj?pid=Api&P=0&h=180",
        likes = 19, comments = 3, shares = 2
    ),
    Post(
        id = "12",
        userName = "Bima",
        userHandle = "bimafood",
        userAvatarUrl = "https://i.pravatar.cc/150?img=12",
        time = "2h",
        content = "Soto Lamongan kuah kuning—koya bawang bikin tambah mantap!",
        imageUrl = "https://thumb.viva.id/vivabanyuwangi/665x374/2024/11/06/672b77409bf95-soto-ayam-lamongan_banyuwangi.jpg",
        likes = 28, comments = 7, shares = 5
    ),
    Post(
        id = "13",
        userName = "Siti Nurlaila",
        userHandle = "sitinur",
        userAvatarUrl = "https://assets.pikiran-rakyat.com/crop/0x0:0x0/750x500/photo/2022/06/06/688520112.jpg",
        time = "45m",
        content = "Lumpia goreng isi rebung dan ayam, kriuk renyah tiap gigitan.",
        imageUrl = null,
        likes = 14, comments = 2, shares = 1
    ),
    Post(
        id = "14",
        userName = "Rendy Nugraha",
        userHandle = "rendynug",
        userAvatarUrl = "https://i.pravatar.cc/150?img=14",
        time = "5h",
        content = "Bebek goreng bumbu hitam, sambal pencit segar pedas seger!",
        imageUrl = "https://tse4.mm.bing.net/th/id/OIP.4eVwUnJz9-LcHafgqjHBrwHaEK?pid=Api&P=0&h=180",
        likes = 39, comments = 10, shares = 6
    ),
    Post(
        id = "15",
        userName = "Aisyah",
        userHandle = "aisyah.id",
        userAvatarUrl = "https://i.pravatar.cc/150?img=15",
        time = "3d",
        content = "Roti sobek homemade isi keju leleh—lembutnya kebangetan.",
        imageUrl = "https://tse4.mm.bing.net/th/id/OIP.PScF7b9e9o0JUVOpFe6xqwHaGK?pid=Api&P=0&h=180",
        likes = 54, comments = 12, shares = 8
    ),
    Post(
        id = "16",
        userName = "Hakim",
        userHandle = "hakimchef",
        userAvatarUrl = "https://i.pravatar.cc/150?img=16",
        time = "9m",
        content = "Cilok kuah pedas, tabur bawang goreng dan daun bawang.",
        imageUrl = null,
        likes = 11, comments = 1, shares = 1
    ),
    Post(
        id = "17",
        userName = "Lukman",
        userHandle = "lukmankitchen",
        userAvatarUrl = "https://i.pravatar.cc/150?img=17",
        time = "1h",
        content = "Cumi bakar kecap manis pedas—wangi smokey-nya dapet!",
        imageUrl = "https://tse2.mm.bing.net/th/id/OIP.Hhav3vqbKDz2kurymv2e0AHaEO?pid=Api&P=0&h=180",
        likes = 31, comments = 6, shares = 4
    ),
    Post(
        id = "18",
        userName = "Putri",
        userHandle = "putricooks",
        userAvatarUrl = "https://i.pravatar.cc/150?img=18",
        time = "12m",
        content = "Pecel sayur dengan bumbu kacang kental dan rempeyek renyah.",
        imageUrl = "https://tse1.mm.bing.net/th/id/OIP.NSWQvlR3ROR73AZnc-SfQAHaE5?pid=Api&P=0&h=180",
        likes = 22, comments = 4, shares = 2
    ),
    Post(
        id = "19",
        userName = "Rizky",
        userHandle = "rizkyrzk",
        userAvatarUrl = "https://i.pravatar.cc/150?img=19",
        time = "7h",
        content = "Sop buntut bening—daging empuk, kuah gurih ringan.",
        imageUrl = null,
        likes = 18, comments = 3, shares = 2
    ),
    Post(
        id = "20",
        userName = "Hanif",
        userHandle = "hanifcanteen",
        userAvatarUrl = "https://i.pravatar.cc/150?img=20",
        time = "22m",
        content = "Tempe mendoan panas, sambal kecap rawit—sesederhana itu, seenak itu.",
        imageUrl = "https://beritajatim.com/wp-content/uploads/2022/10/WhatsApp-Image-2022-10-29-at-09.31.32.jpeg",
        likes = 26, comments = 5, shares = 3
    ),
    Post(
        id = "21",
        userName = "Nadia",
        userHandle = "nadia_kitchen",
        userAvatarUrl = "https://i.pravatar.cc/150?img=21",
        time = "3h",
        content = "Gado-gado siram, kerupuk udang pelengkap wajib!",
        imageUrl = "https://images.unsplash.com/photo-1512621776951-a57141f2eefd",
        likes = 29, comments = 9, shares = 5
    ),
    Post(
        id = "22",
        userName = "Irfan",
        userHandle = "irfanmasak",
        userAvatarUrl = "https://i.pravatar.cc/150?img=22",
        time = "16m",
        content = "Tongseng kambing bumbu pekat, kol dan tomat jangan lupa.",
        imageUrl = "https://tse1.mm.bing.net/th/id/OIP.3u1pSSHNDFxOQEaqUvD6EwHaEK?pid=Api&P=0&h=180",
        likes = 34, comments = 7, shares = 6
    ),
    Post(
        id = "23",
        userName = "Selena",
        userHandle = "selenafood",
        userAvatarUrl = "https://i.pravatar.cc/150?img=23",
        time = "9h",
        content = "Cheesecake panggang—teksturnya halus, manisnya pas.",
        imageUrl = "https://shopee.co.id/inspirasi-shopee/wp-content/uploads/2019/06/basque-burnt-cheesecake-jajabakes.webp",
        likes = 41, comments = 13, shares = 7
    ),
    Post(
        id = "24",
        userName = "Yusuf",
        userHandle = "yusufcook",
        userAvatarUrl = "https://i.pravatar.cc/150?img=24",
        time = "2d",
        content = "Nasi uduk wangi santan, lauk telur balado dan orek tempe.",
        imageUrl = "https://bimamedia-gurusiana.ap-south-1.linodeobjects.com/ef56dd28c084d86aeb28f3b391e94548/2020/06/01/thumbnail-img20200601204852c224150a06338a4d9092f5314a7b781f20200601204921-bimacms.JPG",
        likes = 21, comments = 4, shares = 2
    ),
    Post(
        id = "25",
        userName = "Naya",
        userHandle = "naya.kitchen",
        userAvatarUrl = "https://i.pravatar.cc/150?img=25",
        time = "6m",
        content = "Dimsum ayam jamur homemade—cocol saus chili oil.",
        imageUrl = "https://tse3.mm.bing.net/th/id/OIP.n6pKYAq3SNzIiSLLcBqu-AHaEb?pid=Api&P=0&h=180",
        likes = 37, comments = 8, shares = 4
    ),
    Post(
        id = "26",
        userName = "Zidan",
        userHandle = "zidanfood",
        userAvatarUrl = "https://i.pravatar.cc/150?img=26",
        time = "11h",
        content = "Sate taichan sambal jeruk limau—segar pedasnya nampol.",
        imageUrl = null,
        likes = 15, comments = 2, shares = 1
    ),
    Post(
        id = "27",
        userName = "Maya",
        userHandle = "mayaeats",
        userAvatarUrl = "https://i.pravatar.cc/150?img=27",
        time = "40m",
        content = "Bakso urat lava—isi cabai rawit, pedasnya meledak!",
        imageUrl = "https://tse2.mm.bing.net/th/id/OIP.UMOcb5btCkdH77SD5-EpIwHaE4?pid=Api&P=0&h=180",
        likes = 58, comments = 16, shares = 9
    ),
    Post(
        id = "28",
        userName = "Daffa",
        userHandle = "daffakitchen",
        userAvatarUrl = "https://i.pravatar.cc/150?img=28",
        time = "4h",
        content = "Crispy chicken skin—krispi di luar, juicy di dalam.",
        imageUrl = "https://tse3.mm.bing.net/th/id/OIP.jhWdrPtu5HLUtw8wfCO5UwHaDt?pid=Api&P=0&h=180",
        likes = 44, comments = 12, shares = 6
    ),
    Post(
        id = "29",
        userName = "Intan",
        userHandle = "intaneats",
        userAvatarUrl = "https://i.pravatar.cc/150?img=29",
        time = "1m",
        content = "Rujak buah segar, bumbu gula merah kacang—favorit sore hari.",
        imageUrl = "https://d1vbn70lmn1nqe.cloudfront.net/prod/wp-content/uploads/2023/07/21044831/ini-resep-pecel-yang-praktis-dan-menggugah-selera.jpg.webp",
        likes = 13, comments = 1, shares = 1
    ),
    Post(
        id = "30",
        userName = "Fajar",
        userHandle = "fajarchef",
        userAvatarUrl = "https://i.pravatar.cc/150?img=30",
        time = "2d",
        content = "Es kopi susu aren, less ice biar rasa kopinya lebih kuat.",
        imageUrl = "https://tse3.mm.bing.net/th/id/OIP.k8XfokHCRakVy4hrVjgltAHaEK?pid=Api&P=0&h=180",
        likes = 52, comments = 9, shares = 5
    )
)

