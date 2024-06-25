const debug = require("debug")("app:auth-middleware");
const { verifyToken } = require("../utils/jwt.tools")
const User = require("../model/user.model");
const ROLES = require("../data/roles.constants.json")


const middlewares = {};
const PREFIX = "Bearer";

middlewares.authentication = async (req, res, next) => {
  try {
    const { authorization } = req.headers;
    if (!authorization) {
      return res.status(401).json({ error: "User not authenticated1" });
    }

    const [prefix, token] = authorization.split(" ");
    if (prefix !== PREFIX) {
      return res.status(401).json({ error: "User not authenticated2" });
    }

    if (!token) {
      return res.status(401).json({ error: "User not authenticated3" });
    }

    const payload = await verifyToken(token);
    if (!payload) {
      return res.status(401).json({ error: "User not authenticated4" });
    }

    const userId = payload["sub"];

    const user = await User.findById(userId);
    if (!user) {
      return res.status(401).json({ error: "User not authenticated5" });
    }

    const isTokenValid = user.tokens.includes(token);
    if (!isTokenValid) {
      return res.status(401).json({ error: "User not authenticated6" });
    }

    req.user = user;
    req.token = token;

    next();
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: "Internal Server Error" });
  }
}

middlewares.authorization = (roleRequired = ROLES.SYSADMIN) => {
  return (req, res, next) => {
    try {
      const { rol = [] } = req.user;
      const isAuth = rol.includes(roleRequired);
      const isSysAdmin = rol.includes(ROLES.ADMIN);

      if (!isAuth && !isSysAdmin) {
        return res.status(403).json({ error: "Forbidden" });
      }

      next();
    } catch (error) {
      console.error(error);
      return res.status(500).json({ error: "Internal Server Error authorization" });
    }
  }
}

module.exports = middlewares;