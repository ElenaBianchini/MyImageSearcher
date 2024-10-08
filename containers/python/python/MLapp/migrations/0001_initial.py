# Generated by Django 4.2.16 on 2024-09-13 14:17

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Photo',
            fields=[
                ('id', models.IntegerField(primary_key=True, serialize=False)),
                ('embedding', models.JSONField()),
                ('date_insert', models.DateField()),
                ('date_update', models.DateField()),
                ('is_active', models.BooleanField()),
            ],
            options={
                'ordering': ['-id'],
            },
        ),
    ]
