const express = require('express');

const Model = require('../model/product.model')

const Payment = require('../model/tarjetas.model')
const User = require('../model/user.model')
const router = express.Router()
const ROLES = require("../data/roles.constants.json");
const productController = require('../controllers/product.controller')
const { authentication, authorization } = require("../middlewares/auth.middleware");



const { idInParams, idenInParams, } = require("../validators/product.validator");
const validateFields = require("../validators/index.validator");


// Insert Product: POST
router.post('/postProduct', async (req, res) => {
    console.log('Received POST request at /postProduct');
    console.log('Request body:', req.body);

    const data = new Model({
        nombre: req.body.nombre,
        descripcion: req.body.descripcion,
        precio: req.body.precio,
        ubicacion: req.body.ubicacion,
        calificacion: req.body.calificacion || 0, // Valor por defecto
        imagenes: req.body.imagenes || [], // Valor por defecto
        fecha: req.body.fecha || new Date(), // Valor por defecto
        user: req.body.user
    });

    try {
        const dataToSave = await data.save();
        res.status(200).json({ "result": "ok" });
    } catch (error) {
        console.error('Error saving product:', error.message);
        res.status(400).json({ "result": error.message });
    }
});

//Insertar tarjeta: POST
router.post('/postPayment', async (req, res) =>{

    const data = new Payment({
        titular: req.body.titular,
        number: req.body.number,
        cvv: req.body.cvv,
        fechaVencimiento: req.body.fechaVencimiento,
        user: req.body.user 
    })

    try{
        const dataToSave = await data.save()
        res.status(200).json({"result": "ok"})
    }
    catch(error){
        console.error('Error saving product:', error.message);
        res.status(400).json({"result": error.message})
    }

});

//Get all products

router.get('/getAllProducts', async (req, res) => {

    try {
        const data = await Model.find()
        res.status(200).json(data)
    }
    catch (error) {
        res.status(400).json({ "result": error.message })
    }

});
// Get all Blocked products
router.get('/getAllActiveProducts', async (req, res) => {
    try {
        // Encuentra todos los usuarios cuyo estado es inactivo
        const inactiveUsers = await User.find({ userState: true }, '_id');
        const inactiveUserIds = inactiveUsers.map(user => user._id);

        // Encuentra todos los productos cuyo user está en la lista de usuarios inactivos
        const blockedProducts = await Model.find({ user: { $in: inactiveUserIds } });

        res.status(200).json(blockedProducts);
    } catch (error) {
        res.status(400).json({ "result": error.message });
    }
});

// Get all Blocked products
router.get('/getAllBlockedProducts', async (req, res) => {
    try {
        // Encuentra todos los usuarios cuyo estado es inactivo
        const inactiveUsers = await User.find({ userState: false }, '_id');
        const inactiveUserIds = inactiveUsers.map(user => user._id);

        // Encuentra todos los productos cuyo user está en la lista de usuarios inactivos
        const blockedProducts = await Model.find({ user: { $in: inactiveUserIds } });

        res.status(200).json(blockedProducts);
    } catch (error) {
        res.status(400).json({ "result": error.message });
    }
});


//Get one product
router.get('/getProduct/:id', async (req, res) => {

    const { id } = req.params;

    try {
        const data = await Model.findOne({ _id: id })
        res.json(data)
    }
    catch (error) {
        res.status(400).json({ "result": error.message })
    }

});

// Función para encontrar productos por ID de usuario
router.get('/getUserProducts/:id', async (req, res) => {

    const { id } = req.params;
    try {
        const products = await Model.find({ user: id });
        res.json(products);
    } catch (error) {
        res.status(400).json({ "result": error.message })

    }
})



// Función para encontrar tarjetas por ID de usuario
router.get('/getPayment/:id', async (req, res) =>{ 

    const{id} = req.params;
    try {
        const methods = await Payment.find({ user: id });
        res.json(methods);
    } catch (error) {
        res.status(400).json({"result": error.message})
        
    }
})


//Update product

router.patch('/updateProduct/:id', async (req, res) => {

    try {
        const id = req.query.id;
        const updatedData = req.body;
        const options = { new: true };


        const result = await Model.findByIdAndUpdate(
            id, updatedData, options
        );
        res.status(200).json({ "result": "ok" })
    }
    catch (error) {
        res.status(400).json({ "result": error.message })
    }

});



//Delete Product
router.delete('/deleteProduct/:id', async (req, res) => {

    try {
        const { id } = req.params;


        const result = await Model.findOneAndDelete({ _id: id })
        res.status(200).json({ "result": "ok" })
    }
    catch (error) {
        res.status(400).json({ "result": error.message })
    }

});

// Endpoint para agregar productos al carrito
router.patch('/onCart/:id', authentication, async (req, res) => {
    try {
        const userId = req.user._id;
        const { id: productId } = req.params;

        const user = await User.findById(userId);
        if (!user) {
            return res.status(404).json({ message: 'User not found' });
        }

        const index = user.onCartProducts.indexOf(productId);
        if (index > -1) {
            return res.status(400).json({ message: 'Product already in cart' });
        } else {
            user.onCartProducts.push(productId);
        }

        await user.save();
        res.status(200).json({ message: 'Product added to cart', onCartProducts: user.onCartProducts });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

//Obtener los productos en el carrito
router.get("/getOnCart",
    authentication,
    authorization(ROLES.USER),
    productController.findOnCartProducts
);

router.get('/getLikes', authentication, async (req, res) => {
    try {
        const userId = req.user._id;
        const user = await User.findById(userId).populate('likedProducts');
        if (!user) {
            return res.status(404).json({ message: 'User not found' });
        }
        res.status(200).json(user.likedProducts);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: error.message });
    }
});

// Obtener productos comprados
router.get('/getShopped', authentication, async (req, res) => {
    try {
        const userId = req.user._id;
        const user = await User.findById(userId).populate('shoppedProducts');

        if (!user) {
            return res.status(404).json({ message: 'User not found' });
        }

        res.status(200).json(user.shoppedProducts);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// Endpoint para agregar/quitar producto de favoritos
router.patch('/like/:id', authentication, async (req, res) => {
    try {
        const userId = req.user._id;
        const { id: productId } = req.params;

        const user = await User.findById(userId);
        if (!user) {
            return res.status(404).json({ message: 'User not found' });
        }

        const index = user.likedProducts.indexOf(productId);
        if (index > -1) {
            user.likedProducts.splice(index, 1); // Quitar de favoritos
        } else {
            user.likedProducts.push(productId); // Agregar a favoritos
        }

        await user.save();
        res.status(200).json({ message: 'Favorites updated', likedProducts: user.likedProducts });
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: error.message });
    }
});


//Finalizar compra y agregar producto a ShoppedProducts
router.patch("/shopped/:id",
    authentication,
    authorization(ROLES.USER),
    idInParams,
    validateFields,
    productController.ShoppedToggleProduct
);

//Agregar a productos en venta
router.patch("/onSale/:id",
    authentication,
    authorization(ROLES.USER),
    idInParams,
    validateFields,
    productController.onSaleToggleProduct
);

//Obtener los productos en venta
//Obtener los productos comprados por el usuario
router.get("/getOnSale",
    authentication,
    authorization(ROLES.USER),
    productController.findOnSale
);

//Guardar datos de pago

// Obtener productos por usuario
router.get('/getUserProducts/:userId', async (req, res) => {
    const { userId } = req.params;

    try {
        const products = await Product.find({ user: userId });
        res.status(200).json(products);
    } catch (error) {
        res.status(400).json({ "result": error.message });
    }
});

// Rutas para actualizar la imagen de perfil
router.patch('/updateProfilePicture/:id', async (req, res) => {
    const { id } = req.params;
    const { profilePicture } = req.body;

    try {
        const user = await User.findById(id);
        if (!user) {
            return res.status(404).json({ error: "User not found" });
        }
        user.profilePicture = profilePicture;
        await user.save();
        return res.status(200).json(user);
    } catch (error) {
        return res.status(400).json({ result: error.message });
    }
});




module.exports = router;

