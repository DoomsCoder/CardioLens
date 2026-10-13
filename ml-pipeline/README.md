# 🧠 CardioLens ML & Algorithm Pipeline

Welcome to the ML pipeline workspace for CardioLens! This directory is dedicated to offline dataset preprocessing, neural network model experimentation, TFLite model optimization, and Explainable AI (XAI) generation.

---

## 🏗️ Architecture & Division of Scope

To ensure optimal on-device performance and minimal frame latency, the pipeline follows a clear separation of concerns:

- **Android Client Scope (`/app`):**
  - Real-time video capture via CameraX and ML Kit ROI extraction.
  - Native runtime implementation of DSP filters (Butterworth bandpass, spatial color averaging).
  - TFLite Model Execution and mapping outputs to plain-language Explainability UI.

- **ML Workspace Scope (`/ml-pipeline`):**
  - Dataset ingestion and preprocessing pipelines (e.g., UBFC-rPPG).
  - Deep Learning architecture development (1D-CNN, MobileNet).
  - **SHAP Explainability Analysis:** Running SHAP on the validation set to extract global feature attributions and identify which waveform characteristics drive the HRV/Stress predictions.
  - Exporting, quantizing, and packaging trained weights into a compact `.tflite` model.

> **Note for Contributors:** You do not need to build deterministic DSP filters or write runtime camera integration code here. Focus exclusively on dataset preparation, neural network training, SHAP feature importance plots, and `.tflite` model export.

---

## 📁 Directory Structure

```text
ml-pipeline/
├── data/           # Raw & preprocessed datasets (git-ignored)
├── notebooks/      # Exploratory analysis, training, and SHAP visualizations
├── src/            # Modular Python code (preprocessing, network definitions)
├── models/         # Final exported .tflite models
└── README.md