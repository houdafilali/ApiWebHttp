import tensorflow as tf

# Chemin vers le fichier h5 contenant le modèle


# Charger le modèle à partir du fichier h5
modele = tf.keras.models.load_model("model.h5")

# Afficher un résumé du modèle
modele.summary()