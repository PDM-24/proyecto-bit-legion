const debug = require("debug")("app:auth-middleware");
const  { verifyToken } = require("../utils/jwt.tools")
const User = require("../model/user.model");
const ROLES = require("../data/roles.constants.json")


const middlewares = {};
const PREFIX = "Bearer";

middlewares.authentication = async (req, res, next) => {
    try {
     
  
      //Verificar la autorizacion
      const { authorization } = req.headers;
   // debug(req.headers);
      if(!authorization) {
          return res.status(401).json({ error: "User not authenticated1" });
      }
  
      //Verificar la validez del token
  
      const [prefix, token] = authorization.split(" ");
  
      if(prefix !== PREFIX) {
          return res.status(401).json({ error: "User not authenticated2" });
      }
      
      if(!token) {
          return res.status(401).json({ error: "User not authenticated3" });
      }
  
      const payload = await verifyToken(token);
      if(!payload) {
          return res.status(401).json({ error: "User not authenticated4" });
      }
  
      const userId = payload["sub"];
  
      //Verificar el usuario
      const user = await User.findById(userId);
  
      if(!user) {
          return res.status(401).json({ error: "User not authenticated5" });
      }
  
      //Comparar el token con los tokens registrados
      const isTokenValid = user.tokens.includes(token);
      if(!isTokenValid) {
          return res.status(401).json({ error: "User not authenticated6" });
      }
  
      //Modificar la req, para incluir la info del usuario
      req.user = user;
      req.token = token;
      
      next();
    } catch (error) {
      console.error;
      return res.status(500).json({ error: "Internal Server Error" });
    }
  }

  
middlewares.authorization = (roleRequired = ROLES.SYSADMIN) => {
    return (req, res, next) =>{
     //Antes de este middleware debe haber pasado por la autenticación
      try{
        const {roles = []} = req.user;
        //Se verifica que el rol requerido este en la colección
        const isAuth = roles.includes(roleRequired);
        const isSysAdmin = roles.includes(ROLES.SYSADMIN);
  
        //Si el rol no se encuentra o no es SYSADMIN se devuelve error
        if(!isAuth && !isSysAdmin){
          return res.status(403).json({ error: "Forbidden" });
        }
  
        next();
      } 
      catch(error){
        console.error;
        return res.status(500).json({ error: "Internal Server Error authorization" });
      }
    }
  }

  module.exports = middlewares;
  
