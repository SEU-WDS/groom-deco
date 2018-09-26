from pymedtermino.snomedct import *
for concept in SNOMEDCT.search("hemorrhag*"):
    if not concept.is_a(SNOMEDCT[404684003]):
        continue
has_hemorrhage = False
for hemorrhage in SNOMEDCT[50960005].self_and_descendants_no_double():
    if hemorrhage in concept.associated_morphology:
        has_hemorrhage = True
        break
    if not has_hemorrhage:
        print(concept)