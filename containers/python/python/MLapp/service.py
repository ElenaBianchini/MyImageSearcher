from transformers import AutoTokenizer, BertModel, BlipProcessor, BlipForConditionalGeneration
from PIL import Image
import torch
import base64
from io import BytesIO


class ServiceML:
    def __init__(self, threshold=0.3):
        self.threshold = threshold
        self.tokenizer = AutoTokenizer.from_pretrained("google-bert/bert-base-uncased")
        self.bert = BertModel.from_pretrained("google-bert/bert-base-uncased")
        self.processor = BlipProcessor.from_pretrained("Salesforce/blip-image-captioning-base")
        self.model = BlipForConditionalGeneration.from_pretrained("Salesforce/blip-image-captioning-base")

    def get_text_embedding(self, text):
        inputs = self.tokenizer(text, return_tensors='pt', padding=True)
        with torch.no_grad():
            outputs = self.bert(**inputs)

        # Restituisce l'ultimo hidden state medio (pooling di tutti i token)
        return outputs.last_hidden_state.mean(dim=1)


    def create_embedding(self, img_base64):
        # Preprocesso immagine per BLIP
        image_data = base64.b64decode(img_base64)
        image = Image.open(BytesIO(image_data)).convert("RGB")

        inputs = self.processor(image, return_tensors="pt", padding=True)

        # Generare la didascalia
        caption = self.model.generate(**inputs)

        # Convertire il risultato in testo
        output = self.processor.decode(caption[0], skip_special_tokens=True)
        print(output)

        # Restituisce l'embedding 
        return self.get_text_embedding(output)


    def most_similar_images(self,query, image_id_embeddings):
        # Calcolo l'embedding della query
        text_features = self.get_text_embedding(query)

        similarities = []
        cosSimFunc = torch.nn.CosineSimilarity()

        for i, embed in enumerate(image_id_embeddings):
            s = cosSimFunc(text_features, embed[1])
            s = s.item()
            similarities.append((i, s))

        similarities.sort(reverse=True, key=lambda x: x[1])
        print(similarities)
        candidates = [s for s in similarities if s[1] > self.threshold]

        res = []
        for c in candidates:
            i = c[0]
            res.append(image_id_embeddings[i][0])

        return res






