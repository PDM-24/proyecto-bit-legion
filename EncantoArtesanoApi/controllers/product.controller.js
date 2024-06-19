const { json } = require("express");
const Product = require('../model/product.model')
const User =  require('../model/user.model')
const debug = require("debug")("index:product-controller");

const controller = {};




controller.likeAProduct = async (req, res, next) => {
    try{
        const {id} = req.params;
        const user = req.user;

        const product = await Product.findOne({_id: id})
           
           
        if(!product){
            return res.status(404).json({error: "Product not found"});
        }

        let _likes = user["likedProducts"] || [];
        const alreadyLike = _likes.findIndex(_i => _i.equals(id)) >= 0;

        if(alreadyLike){
            _likes = _likes.filter(_i => !_i.equals(id));
        }else{
            _likes = [id, ..._likes];
        }

        user ["likedProducts"] = _likes;

        const newUser = await (await user.save())
            .populate("likedProducts", "nombre precio descripcion imagenes");
        return res.status(200).json(newUser);

    }
    catch(error){
        console.log(req.params)

        //console.log({error})
        next(error);
    }
}


controller.onCartToggleProduct = async (req, res, next) => {
    try{
        const {id} = req.params;
        const user = req.user;

        const product = await Product.findOne({_id: id})
           
           
        if(!product){
            return res.status(404).json({error: "Product not found"});
        }

        let _likes = user["onCartProducts"] || [];
        const alreadyLike = _likes.findIndex(_i => _i.equals(id)) >= 0;

        if(alreadyLike){
            _likes = _likes.filter(_i => !_i.equals(id));
        }else{
            _likes = [id, ..._likes];
        }

        user ["onCartProducts"] = _likes;

        const newUser = await (await user.save())
            .populate("onCartProducts", "nombre precio descripcion imagenes");
        return res.status(200).json(newUser);

    }
    catch(error){
        console.log(req.params)

        //console.log({error})
        next(error);
    }
}


controller.ShoppedToggleProduct = async (req, res, next) => {
    try{
        const {id} = req.params;
        const user = req.user;

        const product = await Product.findOne({_id: id})
           
           
        if(!product){
            return res.status(404).json({error: "Product not found"});
        }

        let _likes = user["shoppedProducts"] || [];
        const alreadyLike = _likes.findIndex(_i => _i.equals(id)) >= 0;

        if(alreadyLike){
            _likes = _likes.filter(_i => !_i.equals(id));
        }else{
            _likes = [id, ..._likes];
        }

        user ["shoppedProducts"] = _likes;

        const newUser = await (await user.save())
            .populate("shoppedProducts", "nombre precio descripcion imagenes");
        return res.status(200).json(newUser);

    }
    catch(error){
        console.log(req.params)

        //console.log({error})
        next(error);
    }
}

controller.findOnCartProducts = async (req, res, next) => {
    try{
        const user =
        await (req.user)
        .populate({
            path: "onCartProducts",
            populate: [
                {
                    path: "user",
                    select: "username"
                }
               
            ]
        });
    
        return res.status(200).json({posts: user["onCartProducts"]});

    }
    catch(error){
        next(error);
    }
}

controller.findLikes = async (req, res, next) => {
    try{
        const user =
        await (req.user)
        .populate({
            path: "likedProducts",
            populate: [
                {
                    path: "user",
                    select: "username"
                }
               
            ]
        });
    
        return res.status(200).json({posts: user["likedProducts"]});

    }
    catch(error){
        next(error);
    }
}

controller.findShopped = async (req, res, next) => {
    try{
        const user =
        await (req.user)
        .populate({
            path: "shoppedProducts",
            populate: [
                {
                    path: "user",
                    select: "username"
                }
               
            ]
        });
    
        return res.status(200).json({posts: user["shoppedProducts"]});

    }
    catch(error){
        next(error);
    }
}


module.exports = controller;