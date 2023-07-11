import tensorflow as tf
# Charger le modèle à partir du fichier h5
modele = tf.keras.models.load_model("model.h5")
# Afficher un résumé du modèle
modele.summary()
