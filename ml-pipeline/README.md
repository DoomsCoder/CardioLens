# 🧠 CardioLens ML & Algorithm Pipeline

Welcome to the ML pipeline workspace for CardioLens! This directory is dedicated to offline dataset preprocessing, neural network model experimentation, and TFLite model optimization.

---

## 🏗️ Architecture & Division of Scope

To ensure optimal on-device performance and minimal frame latency, the pipeline follows a clear separation of concerns:

- **Android Client Scope (`/app`):**
    - Real-time video capture via CameraX.
    - Face landmark tracking and Region of Interest (ROI) extraction.
    - Real-time digital signal processing (DSP) filters (Butterworth bandpass, spatial color averaging).
    - Native runtime implementation of deterministic rPPG baseline algorithms (CHROM / POS / Peak-to-Peak interval detection).

- **ML Workspace Scope (`/ml-pipeline`):**
    - Dataset ingestion and preprocessing pipelines (e.g., UBFC-rPPG, pure face video crops).
    - Deep Learning spatio-temporal architecture development (PhysNet, DeepPhys, 3D-CNN / BiLSTM).
    - Model training, validation, and pulse waveform ground-truth regression.
    - Exporting, quantizing, and packaging trained weights into a compact `.tflite` model.

> **Note for Contributors:** You do not need to build deterministic DSP filters (CHROM/POS) or write runtime camera integration code here. Focus exclusively on dataset preparation, neural network training, evaluation metrics, and `.tflite` model export.

---

## 📁 Directory Structure

```text
ml-pipeline/
├── data/           # Raw & preprocessed datasets (git-ignored)
├── notebooks/      # Exploratory analysis & training experiments
├── src/            # Modular Python code (preprocessing, network definitions)
├── models/         # Final exported .tflite models & evaluation artifacts
└── README.md