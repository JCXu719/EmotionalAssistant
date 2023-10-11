import torch
from transformers import RobertaTokenizerFast, RobertaForSequenceClassification
from os.path import dirname, join

def getFileName():
    filename = join(dirname(__file__),"emotion-english-distilroberta-base")
    return filename

def main(input_string):
    model = RobertaForSequenceClassification.from_pretrained(getFileName(), local_files_only=True)
    tokenizer = RobertaTokenizerFast.from_pretrained(getFileName(), local_files_only=True)

    inputs = tokenizer(input_string, return_tensors="pt")

    with torch.no_grad():
        logits = model(**inputs).logits

    predicted_class_id = logits.argmax().item()
    return model.config.id2label[predicted_class_id]
