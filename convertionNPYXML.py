import numpy as np
import xml.etree.ElementTree as ET

# Chargement du fichier .npy
data = np.load('signals.npy')

# Création de l'élément racine pour le fichier XML
root = ET.Element('Matrices')

# Parcours des matrices
for matrix in data:
    # Création de l'élément pour chaque matrice
    matrix_element = ET.SubElement(root, 'Matrix')
    matrix_element.set('nbRows', '5000')
    matrix_element.set('nbColumns', '1')

    # Parcours des lignes de la matrice
    for value in matrix:
        # Création de l'élément pour chaque ligne
        row_element = ET.SubElement(matrix_element, 'Row')
        row_element.text = str(value)

# Création de l'objet ElementTree avec l'élément racine
tree = ET.ElementTree(root)

# Enregistrement du fichier XML
tree.write('matrices.xml')
