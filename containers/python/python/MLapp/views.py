from django.shortcuts import render
from django.http import JsonResponse

import json
import torch
from datetime import date

from .service import ServiceML
from .models import Photo

# Create your views here.
def load_image(request):
    if request.method == 'POST':
        try: 
            body_data = json.loads(request.body) 
            # { 
            #   id: id_image,
            #   img_content: image_base64
            # } 

            id = body_data.get('id')
            img_base64 = body_data.get('img_content')
            embedding = ServiceML.create_embedding(img_base64)

            # Da tensore a lista per salvarlo nel db
            embedding = embedding.tolist() 

            new_record = Photo.objects.create(
                id = id,
                embedding = embedding,
                date_insert = date.today(),
                date_update = date.today(),
                is_active = True,
            )

            return JsonResponse({"status": "success"})
        
        except Exception as e:
            return JsonResponse({"status": "error", "message": str(e)}, status=500)
        
    else:
        return JsonResponse({"error": "Only POST request"}, status=400)


def delete_photo(request):
    if request.method == 'POST':
        try: 
            body_data = json.loads(request.body) 
            # { 
            #   id: id_image,
            # } 

            id = body_data.get('id')
            photo = Photo.objects.get(id=id)
            photo.is_active = False

            return JsonResponse({"status": "success"})
        
        except Exception as e:
            return JsonResponse({"status": "error", "message": str(e)}, status=500)
        
    else:
        return JsonResponse({"error": "Only POST request"}, status=400)
    

def search_images(request):
    if request.method == 'POST':
        try: 
            body_data = json.loads(request.body) 
            # { 
            #   query: query_text,
            #   id_images : [<ids>]
            # } 

            query = body_data.get('query')
            id_images = body_data.get('id_images')
            
            photos = Photo.objects.filter(id__in=id_images)
            image_id_embeddings = []
            
            for photo in photos:
                image_id_embeddings.append((photo.id, torch.tensor(photo.embedding)))

            image_ids = ServiceML.most_similar_images(query, image_id_embeddings)

            return JsonResponse({"status": "success", "result": image_ids})
        
        except Exception as e:
            return JsonResponse({"status": "error", "message": str(e)}, status=500)
        
    else:
        return JsonResponse({"error": "Only POST request"}, status=400)
