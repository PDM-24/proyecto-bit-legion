const express = require('express');

const User = require('../model/user.model')
const authController = require("../controllers/auth.controller")
const router = express.Router()
const ROLES = require('../data/roles.constants.json')

//Create user: POST
router.post('/createUser', async (req, res) =>{

    const data = new User({
        username: req.body.username,
        password: req.body.password,
        fechaNac: req.body.fechaNac,
        rol: ROLES.USER
    })

    try{
        const dataToSave = await data.save()
        res.status(200).json({"result": "ok"})
    }
    catch(error){
        res.status(200).json({"result": error.message})
    }

});

//Obtener todos los usuarios

router.get('/getAllUsers', async (req, res) =>{

    try{
        const data = await User.find()
        res.status(200).json(data)
    }
    catch(error){
        res.status(400).json({"result": error.message})
    }

});


//Auth registro de usuario
router.post(["/register"],
   //registerValidator,
    //runValidator,
    authController.register
);

//Auth registro de usuario tipo Adminitrador
router.post(["/registerAdmin"],
    //registerValidator,
     //runValidator,
     authController.registerAdmin
 );

//Auth login
router.post("/login", 
    authController.login);

//Actualizar usuario
router.patch("/update/:id",
   
    authController.update
);


//Get one user
router.get('/getUser/:id', async (req, res) =>{

    const{id}=req.params; 
  
    try{
        const data = await User.findOne({_id: id})
        res.json(data)
    }
    catch(error){
        res.status(400).json({"result": error.message})
    }
  
  });

module.exports = router

// Verificar si el nombre de usuario ya existe
router.get('/checkUsername/:username', async (req, res) => {
    const { username } = req.params;

    try {
        const user = await User.findOne({ username: username });
        if (user) {
            res.status(200).json({ exists: true });
        } else {
            res.status(200).json({ exists: false });
        }
    } catch (error) {
        res.status(400).json({ result: error.message });
    }
});
