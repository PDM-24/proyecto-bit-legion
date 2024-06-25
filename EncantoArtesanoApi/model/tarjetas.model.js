const Mongoose  = require('mongoose')


const dataSchema = new Mongoose.Schema(

    {
        titular:{
            type: String,
            require: true,
            trim: true
        },
        number:{
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
        user: {
            type: Mongoose.Schema.Types.ObjectId,
            ref: "user",
            required: true
        }
    }
);

module.exports = Mongoose.model('tarjetas', dataSchema);