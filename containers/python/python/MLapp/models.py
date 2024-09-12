from django.db import models

# Create your models here.
class Photo(models.Model):
    """A typical class defining a model, derived from the Model class."""

    # Fields
    id = models.IntegerField(primary_key=True)
    embedding = models.JSONField()
    date_insert = models.DateField()
    date_update = models.DateField()
    is_active = models.BooleanField()

    # Metadata
    class Meta:
        ordering = ['-id']
        app_label = 'MLapp'

