from transformers import CLIPProcessor, CLIPModel
from PIL import Image
import torch
import base64
from io import BytesIO


def create_embedding(img_base64):
    # Importo il modello preallenato
    model = CLIPModel.from_pretrained("openai/clip-vit-base-patch32")
    processor = CLIPProcessor.from_pretrained("openai/clip-vit-base-patch32")

    # Preprocesso immagine per CLIP
    image_data = base64.b64decode(img_base64)
    image = Image.open(BytesIO(image_data))
    inputs = processor(images=image, return_tensors="pt")

    # Calcolo l'embedding dell'immagine
    with torch.no_grad():
        image_features = model.get_image_features(**inputs)

    # Normalizzo l'embedding dell'immagine
    image_features = image_features / image_features.norm(p=2, dim=-1, keepdim=True)

    return image_features


def most_similar_images(query, image_id_embeddings, threshold = 0.3):
    # Importo il modello preallenato
    model = CLIPModel.from_pretrained("openai/clip-vit-base-patch32")
    processor = CLIPProcessor.from_pretrained("openai/clip-vit-base-patch32")

    # Preprocesso il testo per CLIP
    text_inputs = processor(text=[query], return_tensors="pt", padding=True)

    # Calcolo l'embedding del testo
    with torch.no_grad():
        text_features = model.get_text_features(**text_inputs)

    # Normalizzo l'embedding del testo
    text_features = text_features / text_features.norm(p=2, dim=-1, keepdim=True)
    
    similarities = []
    cosSimFunc = torch.nn.CosineSimilarity()

    for i, embed in enumerate(image_id_embeddings):
        s = cosSimFunc(text_features, torch.Tensor(embed[1]))
        s = s.item()
        similarities.append((i, s))
    
    similarities.sort(reverse=True, key=lambda x: x[1])
    print(similarities)
    candidates = [s for s in similarities if s[1] > threshold]

    res = []
    for c in candidates:
        i = c[0]
        res.append(image_id_embeddings[i][0])

    return res


# TEST:
image_path = "./dog.jpeg"
with open(image_path, "rb") as img_file:
    img_base64 = base64.b64encode(img_file.read()).decode('utf-8')

emb = create_embedding(img_base64)

id_embs = [(15, emb)]

image_path = "./palme.jpg"
with open(image_path, "rb") as img_file:
    img_base64 = base64.b64encode(img_file.read()).decode('utf-8')

emb = create_embedding(img_base64)

id_embs.append((5,emb))
r = most_similar_images('', id_embs)
print(r)




