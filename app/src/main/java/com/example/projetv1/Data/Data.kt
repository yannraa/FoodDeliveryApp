package com.example.projetv1.Data

import com.example.projetv1.Models.Restaurant
import com.example.projetv1.Models.Menu
import com.example.projetv1.Models.TypeOfMenu
import com.example.projetv1.Models.Cart
import com.example.projetv1.Models.Menu2
import com.example.projetv1.R


// 1---- List of restaurants
val restaurantList = listOf(
    Restaurant(
        restId = 1,
        restIsOpen = false,
        restName = "La Signature",
        restImg = R.drawable.pasta,
        restAddress = "El Biar",
        restType = "Gastro",
        restRating = 3.9f,
        restPhone = "+213 799308766",
        restEmail = "contact@delice-algerien.com",
        restReviewCount = 22,
        restDistance = "15km",
        restMenuList = listOf(
            Menu(
                menuId = 1,
                menuType = TypeOfMenu.Starters,
                menuName = "Salade Cesar",
                // dishPic = R.drawable.salade,
                menuPrice = 600.00,
                menuRating = 3.7f,
                menuDescription = "Délicieuse salade avec laitue croquante, poulet grillé, parmesan et croûtons dorés, accompagnée de notre sauce César maison"
            ),
            Menu(
                menuId = 2,
                menuType = TypeOfMenu.Starters,
                menuName = "Bol de ramen",
                //dishPic = R.drawable.ramen,
                menuPrice = 1200.00,
                menuRating = 3.9f,
                menuDescription = "Bol de nouilles ramen avec bouillon savoureux, porc effiloché, œuf mariné, champignons, et légumes frais.",
            ),
            Menu(
                menuId = 3,
                menuType = TypeOfMenu.Meals,
                menuName = "Entrecote de Boeuf",
                //dishPic = R.drawable.entrecote,
                menuPrice = 2000.00,
                menuRating = 4.4f,
                menuDescription = "Entrecôte de bœuf tendre et juteuse, parfaitement grillée, servie avec une sauce au choix et des légumes de saison",
            ),
            Menu(
                menuId = 4,
                menuType = TypeOfMenu.Meals,
                menuName = "Lasagne",
                // dishPic = R.drawable.lasagne,
                menuPrice = 1200.00,
                menuRating = 4.1f,
                menuDescription = "Lasagnes maison, riches en sauce tomate, viande hachée, béchamel onctueuse, et gratinées au fromage."
            ),
            Menu(
                menuId = 5,
                menuType = TypeOfMenu.Desserts,
                menuName = "Tiramisu",
                // dishPic = R.drawable.pizza,
                menuPrice = 800.00,
                menuRating = 4.1f,
                menuDescription = ""
            ),


            )

    ),
    Restaurant(
        restId = 2,
        restIsOpen = true,
        restName = "PizzaHut",
        restImg = R.drawable.salade,
        restAddress = "AIN ALLAH, Dély Ibrahim",
        restType = "Pizzeria",
        restRating = 3.6f,
        restPhone = "+213 554819958",
        restEmail = "info@Pizza-Hut.com",
        restReviewCount = 11,
        restDistance = "22km",
        restMenuList = listOf(
            Menu(
                menuId = 6,
                menuType = TypeOfMenu.Meals,
                menuName = "Pizza pouleto",
                // dishPic = R.drawable.pizza,
                menuPrice = 850.00,
                menuRating = 4.1f,
                menuDescription = "Pizza généreuse garnie de poulet mariné, poivrons, olives et fromage fondant, sur une base de sauce tomate."

            ),
            Menu(
                menuId = 19,
                menuType = TypeOfMenu.Meals,
                menuName = "Pizza du Chef",
                // dishPic = R.drawable.pizza,
                menuPrice = 1000.00,
                menuRating = 4.1f,
                menuDescription = "Pizza généreuse garnie de poulet mariné, poivrons, olives et fromage fondant, sur une base de sauce tomate."
            ),
        )
    ),
    Restaurant(
        restId = 3,
        restIsOpen = false,
        restName = "Skander Kebab",
        restImg = R.drawable.pasta,
        restAddress = "Vieux Kouba",
        restType = "Kebabier",
        restRating = 4.3f,
        restPhone = "+213 554819958",
        restEmail = "info@Saknder-Kebab.com",
        restReviewCount = 18,
        restDistance = "11km",
        restMenuList = listOf(
            Menu(
                menuId = 7,
                menuType = TypeOfMenu.Starters,
                menuName = "Salade Cesar",
                //dishPic = R.drawable.kebab,
                menuPrice = 600.00,
                menuRating = 4.2f,
                menuDescription = "Sandwich au kebab garni de viande épicée, salade fraîche, tomates, oignons et sauce blanche."
            ),
        )
    ),
    Restaurant(
        restId = 4,
        restIsOpen = false,
        restName = "Le Perchoir",
        restImg = R.drawable.salade,
        restAddress = "P2RH+2V9, Hydra",
        restType = "Gastro",
        restRating = 4.2f,
        restPhone = "+213 560959926",
        restEmail = "info@Saknder-Kebab.com",
        restReviewCount = 27,
        restDistance = "17km",
        restMenuList = listOf(
            Menu(
                menuId = 8,
                menuType = TypeOfMenu.Starters,
                menuName = "Salade Cesar",
                //dishPic = R.drawable.salade,
                menuPrice = 600.00,
                menuRating = 4.2f,
                menuDescription = "Délicieuse salade avec laitue croquante, poulet grillé, parmesan et croûtons dorés, accompagnée de notre sauce César maison"
            ),
            Menu(
                menuId = 9,
                menuType = TypeOfMenu.Starters,
                menuName = "Bol de ramen",

                // dishPic = R.drawable.ramen,
                menuPrice = 1200.00,
                menuRating = 3.9f,
                menuDescription = "Bol de nouilles ramen avec bouillon savoureux, porc effiloché, œuf mariné, champignons, et légumes frais.",
            ),
            Menu(
                menuId = 10,
                menuType = TypeOfMenu.Meals,
                menuName = "Entrecote de Boeuf",
                // dishPic = R.drawable.entrecote,
                menuPrice = 2000.00,
                menuRating = 4.4f,
                menuDescription = "Entrecôte de bœuf tendre et juteuse, parfaitement grillée, servie avec une sauce au choix et des légumes de saison",
            ),
        )
    ),
    Restaurant(
        restId = 5,
        restIsOpen = false,
        restName = "Aqua-Tacos",
        restImg = R.drawable.pasta,
        restAddress = "Bab Ezzouar 16342",
        restType = "Fast food",
        restRating = 4.3f,
        restPhone = "+213 776482424",
        restEmail = "info@AQUA.com",
        restReviewCount = 8,
        restDistance = "11km",
        restMenuList = listOf(
            Menu(
                menuId = 11,
                menuType = TypeOfMenu.Meals,
                menuName = " Tacos " ,
                // dishPic = R.drawable.tacos,
                menuPrice = 600.00,
                menuRating = 4.2f,
                menuDescription = "Galette de blé farcie de viande, frites croustillantes, fromage fondu, et sauce signature."


            ),
        ),
    ),
    Restaurant(
        restId = 6,
        restIsOpen = false,
        restName = "Resto 6",
        restImg = R.drawable.salade,
        restAddress = "Vieux Kouba",
        restType = "Kebabier",
        restRating = 4.3f,
        restPhone = "+213 554819958",
        restEmail = "info@Saknder-Kebab.com",
        restReviewCount = 18,
        restDistance = "22km",
        restMenuList = listOf(
            Menu(
                menuId = 9,
                menuType = TypeOfMenu.Starters,
                menuName = "Salade Cesar",
                //dishPic = R.drawable.kebab,
                menuPrice = 600.00,
                menuRating = 4.2f,
                menuDescription = "Sandwich au kebab garni de viande épicée, salade fraîche, tomates, oignons et sauce blanche."
            ),
        )
    ),
    Restaurant(
        restId = 7,
        restIsOpen = false,
        restName = "Resto 7",
        restImg = R.drawable.pasta,
        restAddress = "Vieux Kouba",
        restType = "Kebabier",
        restRating = 4.3f,
        restPhone = "+213 554819958",
        restEmail = "info@Saknder-Kebab.com",
        restReviewCount = 18,
        restDistance = "15km",
        restMenuList = listOf(
            Menu(
                menuId = 10,
                menuType = TypeOfMenu.Starters,
                menuName = "Salade Cesar",
                //dishPic = R.drawable.kebab,
                menuPrice = 600.00,
                menuRating = 4.2f,
                menuDescription = "Sandwich au kebab garni de viande épicée, salade fraîche, tomates, oignons et sauce blanche."
            ),
        )
    ),
    // Ajoute d'autres restaurants ici
)

// 2----  Cart with list of Menus
val cartList = listOf(
    Cart(
        cartId = 1,
        cartResto = restaurantList.get(0).restName,
        cartItems = listOf(
            Menu2(
                menuId = 1,
                menuName = restaurantList.get(0).restMenuList.get(0).menuName ,
                menuQtt = 1,
                menuPrice = restaurantList.get(0).restMenuList.get(0).menuPrice
            ),
            Menu2(
                menuId = 2,
                menuName = restaurantList.get(0).restMenuList.get(1).menuName ,
                menuQtt = 1,
                menuPrice = restaurantList.get(0).restMenuList.get(1).menuPrice
            ),
            Menu2(
                menuId = 3,
                menuName = restaurantList.get(0).restMenuList.get(2).menuName ,
                menuQtt = 2,
                menuPrice = restaurantList.get(0).restMenuList.get(2).menuPrice
            ),

            )
    )
)
