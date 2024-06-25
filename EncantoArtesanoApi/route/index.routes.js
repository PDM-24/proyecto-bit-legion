const express = require("express");
const router = express.Router();

const postRouter = require("./routes");
const authRouter = require("./auth.routes");

router.use("/auth", authRouter);
router.use("/post", postRouter);

module.exports = router;