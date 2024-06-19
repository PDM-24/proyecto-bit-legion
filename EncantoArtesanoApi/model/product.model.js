const Mongoose  = require('mongoose')


const dataSchema = new Mongoose.Schema(

    {
        nombre:{
            type: String,
            required: true,
            trim: true
        },
        descripcion:{
            type: String,
            required: true,
            trim: true
        },
        ubicacion:{
            type: String,
            required: true,
            trim: true
        },
        precio:{
            type:  Number,
            required: true,
            
        },
        calificacion:{
            type: Number,
            default: 0
        },
        imagenes: {
            type: [String],
            trim: true,
            required: true,
            default: []
        },
        fecha: {
            type: Date,
            default: Date.now
        },
        user: {
            type: Mongoose.Schema.Types.ObjectId,
            ref: "user",
            required: true
         // No requerido solo para pruebas
         //   required: true
        },
           

    }
);

module.exports = Mongoose.model('product', dataSchema);