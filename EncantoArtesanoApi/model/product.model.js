const Mongoose  = require('mongoose')


const dataSchema = new Mongoose.Schema(

    {
        nombre:{
            type: String,
            require: true,
            trim: true
        },
        descripcion:{
            type: String,
            require: true,
            trim: true
        },
        ubicacion:{
            type: String,
            require: true,
            trim: true
        },
        precio:{
            type:  Number,
            require: true,
            
        },
        calificacion:{
            type: Number,
            require: true,
        },
        imagenes: {
            type: [String],
            trim: true,
            required: true,
            default: []
        },
        fecha: {
            type: Date,
            trim: true
        },
        user: {
            type: Mongoose.Schema.Types.ObjectId,
            ref: "user",
         // No requerido solo para pruebas
         //   required: true
        },
           

    }
);

module.exports = Mongoose.model('product', dataSchema);