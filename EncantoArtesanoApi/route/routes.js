const express = require('express');

const Model = require('../model/product.model')
const router = express.Router()
const ROLES = require("../data/roles.constants.json");
const productController = require('../controllers/product.controller')
const { authentication, authorization } = require("../middlewares/auth.middleware");

const { idInParams, idenInParams,  } = require("../validators/product.validator");
const validateFields = require("../validators/index.validator");



//Insert Product: POST
router.post('/postProduct', async (req, res) =>{
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

router.get('/getAllProducts', async (req, res) =>{

    try{
        const data = await Model.find()
        res.status(200).json(data)
    }
    catch(error){
        res.status(400).json({"result": error.message})
    }

});

//Get one product
router.get('/getProduct/:id', async (req, res) =>{

    const{id}=req.params; 

    try{
        const data = await Model.findOne({_id: id})
        res.json(data)
    }
    catch(error){
        res.status(400).json({"result": error.message})
    }

});

//Update product

router.patch('/updateProduct/:id', async (req, res) =>{

    try{
        const id = req.query.id;
        const updatedData = req.body;
        const options = {new: true};


        const result = await Model.findByIdAndUpdate(
            id, updatedData, options
        );
        res.status(200).json({"result": "ok"})
    }
    catch(error){
        res.status(400).json({"result": error.message})
    }

});

//Delete Product
router.delete('/deleteProduct/:id', async (req, res) =>{

    try{
        const{id}=req.params; 


        const result = await Model.findOneAndDelete({_id: id})
        res.status(200).json({"result": "ok"})
    }
    catch(error){
        res.status(400).json({"result": error.message})
    }

});

//Agregar/Quitar producto al carrito
router.patch("/onCart/:id",
    authentication,
    authorization(ROLES.USER),
    idInParams,
    validateFields,
    productController.onCartToggleProduct
);

//Obtener los productos en el carrito
router.get("/getOnCart",
    authentication,
    authorization(ROLES.USER),
    productController.findOnCartProducts
    );

    //Obtener los productos que tiene likes del usuario
router.get("/getLikes",
    authentication,
    authorization(ROLES.USER),
    productController.findLikes
    );

    //Obtener los productos comprados por el usuario
router.get("/getShopped",
    authentication,
    authorization(ROLES.USER),
    productController.findShopped
    );
    

//Like/Dislike a productos
router.patch("/like/:id",
    authentication,
    authorization(ROLES.USER),
    idInParams,
    validateFields,
    productController.likeAProduct
);

//Finalizar compra y agregar producto a ShoppedProducts
router.patch("/shopped/:id",
    authentication,
    authorization(ROLES.USER),
    idInParams,
    validateFields,
    productController.ShoppedToggleProduct
);

//Guardar datos de pago



module.exports = router;

