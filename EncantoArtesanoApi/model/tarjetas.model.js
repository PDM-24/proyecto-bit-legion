const Mongoose  = require('mongoose')


const dataSchema = new Mongoose.Schema(

    {
        titular:{
            type: String,
            require: true,
            trim: true
        },
        numeroTarjeta:{
            type: String,
            require: true,
            trim: true
        },
        fechaVencimiento:{
            type: String,
            require: true,
            trim: true
        },
        cvv:{
            type: String,
            require: true,
            trim: true
        }, 

    }
);

module.exports = Mongoose.model('tarjetas', dataSchema);