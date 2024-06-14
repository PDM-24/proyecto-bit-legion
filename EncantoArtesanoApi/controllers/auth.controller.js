const User = require("../model/user.model");
const ROLES = require("../data/roles.constants.json");
const debug = require("debug")("index:auth-controller");


const { createToken, verifyToken } = require("../utils/jwt.tools");

const controller = {};

controller.register = async (req, res, next) => {
    try {
      //Obtener la info 
      const { username, password } = req.body;
      
      //Verificar la existencia del correo y el user
      const user = 
          await User.findOne({ $or: [{username: username}] });
  
      if(user){
          return res.status(409).json({ error: "User Already Exists" });
      }
      //Si no exsite lo creamos
      const newUser = new User({
          username: username,
          password: password,
          rol: ROLES.USER
      })
  
      await newUser.save();
  
      return res.status(201).json({ message: "User Created Successfully" });
    } catch (error) {
      debug({ error });
      next(error);
    
    }
  }

  
controller.login = async (req, res, next) => {
  try {
    //Obtener info de usuario; identificador y password
    const { identifier, password} = req.body;

    //Verificar que exista el usuario
    const user = 
      await User.findOne({ $or: [{username: identifier}]});

    //Si no existe retornar 404
    if(!user) { 
      return res.status(404).json({ error: "User not found" });
    }

    //Verificamos la password
    //Si no coincide -> 401
    if(!user.comparePassword(password)) {
      return res.status(401).json({ error: "Incorrect password" });
    }
    
    //Si la password coincide -> login (TODO)
    //Crear un token
    const token = await createToken(user._id);

    //Almacenar token
    //Verificar la integridad de los tokens - mas 5 tokens
    let _tokens = [...user.tokens]
    const _verifyPromises = _tokens.map(async (_t) => {
      const status = await verifyToken(_t);
      return status ? _t : null;
    });

    _tokens = (await Promise.all(_verifyPromises))
      .filter(_t => _t)
      .slice(0, 4);

    _tokens = [token, ..._tokens];
    user.tokens = _tokens;

    await user.save();

    //Devolver toke
    return res.status(200).json({ token })
  } catch (error) {
    next(error);
  }
}

controller.whoami = async (req, res, next) =>{
  try{
    const {_id, username, roles} = req.user;
    return res.status(200).json({
      _id, username, email, roles
    })
  }
  catch(error){
    next(error);
  }
}

module.exports = controller

