const express = require('express');
const multer = require('multer');
const path = require('path');
const router = express.Router();

// Configuración de multer
const storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'uploads/');
    },
    filename: (req, file, cb) => {
        cb(null, `${Date.now()}-${file.originalname}`);
    }
});

const upload = multer({ storage });

router.post('/upload', upload.single('image'), (req, res) => {
    try {
        if (!req.file) {
            return res.status(400).send('No file uploaded.');
        }
        const imageUrl = `http://10.0.2.2:3000/uploads/${req.file.filename}`;
        res.status(200).json({ imageUrl });
    } catch (error) {
        console.error('Error uploading image:', error.message);
        res.status(500).send(error.message);
    }
});

module.exports = router;
