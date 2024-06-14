const express = require('express');

const Model = require('../model/product.model')
const router = express.Router()

//Insert Product: POST
router.post('/postProduct', async (req, res) =>{

    const data = new Model({
        nombre: req.body.nombre,
        descripcipn: req.body.descripcipn,
        precio: req.body.precio,
        calificacion: req.body.calificacion,
        imagenes: req.body.imagenes,
        fecha: req.body.fecha,
        user: req.body.user
    })

    try{
        const dataToSave = await data.save()
        res.status(200).json({"result": "ok"})
    }
    catch(error){
        res.status(200).json({"result": error.message})
    }

});

//Get all products

router.get('/getAllProducts', async (req, res) =>{

    try{
        const data = await Model.find()
        res.status(200).json(data)
    }
    catch(error){
        res.status(200).json({"result": error.message})
    }

});

//Get one product

router.get('/getProduct', async (req, res) =>{

    try{
        const data = await Model.findById(req.query.id)
        res.json(data)
    }
    catch(error){
        res.status(200).json({"result": error.message})
    }

});

//Update product

router.patch('/updateProduct', async (req, res) =>{

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
        res.status(200).json({"result": error.message})
    }

});

//Delete Product
router.delete('/deleteProduct', async (req, res) =>{

    try{
        const id = req.query.id;

        const result = await Model.findByIdAndDelete(id)
        res.status(200).json({"result": "ok"})
    }
    catch(error){
        res.status(200).json({"result": error.message})
    }

});

//Create user
// Crear nuevo usuario: POST


module.exports = router;

