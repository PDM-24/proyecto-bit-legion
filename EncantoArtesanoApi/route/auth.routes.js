const express = require('express');

const User = require('../model/user.model')
const authController = require("../controllers/auth.controller")
const router = express.Router()

//Create user: POST
router.post('/createUser', async (req, res) =>{

    const data = new User({
        username: req.body.username,
        password: req.body.password,
        rol: ROLES.USER,
    })

    try{
        const dataToSave = await data.save()
        res.status(200).json({"result": "ok"})
    }
    catch(error){
        res.status(200).json({"result": error.message})
    }

});

//Auth registro de usuario
router.post("/register",
   //registerValidator,
    //runValidator,
    authController.register
);

//Auth login
router.post("/login", 
    authController.login);



//Update User TODO

//Change user state?

//Get all users

//Get one user

module.exports = router