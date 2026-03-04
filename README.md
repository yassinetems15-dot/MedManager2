# MedManager2
Projet Java POO - Gestion d'un servive hospitalier

## Description

MedManager est une application console développée en Java dans le cadre du module de Programmation Orientée Objet (POO).

Le projet permet de modéliser un système hospitalier en appliquant les concepts fondamentaux de la POO :

- Encapsulation
- Association
- Composition
- Redéfinition de equals() et hashCode()
- Utilisation des collections (List)
- Gestion interactive via un menu console


## Objectif du projet

L’objectif est de modéliser un système hospitalier permettant de gérer :

- 👤 Les patients
- 👨‍⚕ Les médecins
- 🏥 Les services hospitaliers
- 📅 Les rendez-vous (bonus)


## Structure du projet

Le projet contient les classes suivantes :

- Patient.java
- Medecin.java
- ServiceHospitalier.java
- RendezVous.java (bonus)
- Main.java


## Fonctionnalités implémentées

### ✔ Gestion des patients
- Ajouter un patient
- Modifier un patient
- Supprimer un patient
- Afficher la liste des patients

### ✔ Gestion des médecins
- Ajouter un médecin
- Afficher les médecins

### ✔ Services hospitaliers
- Affecter un patient à un service
- Affecter un médecin à un service
- Vérification de la capacité des lits
- Tableau de bord des services

### ✔ Rendez-vous (Bonus)
- Planifier un rendez-vous
- Afficher les rendez-vous


## Concepts POO utilisés

### 🔹 Encapsulation
Tous les attributs sont déclarés private et accessibles via getters/setters.

### 🔹 equals() et hashCode()
Les objets Patient et Medecin sont considérés identiques s’ils possèdent le même ID.

### 🔹 Association
La classe RendezVous relie un Patient et un Medecin sans les posséder.

### 🔹 Composition
Un ServiceHospitalier contient une liste de patients et de médecins.


