<div align="center">

<!-- Logo placeholder: add your logo image to /docs/assets/logo.png and update the line below -->
<!-- <img src="docs/assets/logo.png" alt="CardioLens logo" width="120"/> -->

# CardioLens

**Contactless cardiovascular screening using smartphone camera-based rPPG and explainable on-device AI.**

[![Platform](https://img.shields.io/badge/platform-Android-3DDC84?logo=android&logoColor=white)](#)
[![Language](https://img.shields.io/badge/kotlin-Jetpack%20Compose-7F52FF?logo=kotlin&logoColor=white)](#)
[![ML](https://img.shields.io/badge/inference-TensorFlow%20Lite-FF6F00?logo=tensorflow&logoColor=white)](#)
[![Explainability](https://img.shields.io/badge/XAI-SHAP-blueviolet)](#)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue)](LICENSE)
[![Status](https://img.shields.io/badge/status-Phase%201%20--%20In%20Progress-yellow)](#project-status)

</div>

---

CardioLens turns a standard Android smartphone camera into a contactless biometric sensor. It detects the microscopic, invisible color fluctuations in facial skin caused by blood volume changes with every heartbeat, and estimates **Heart Rate (HR)** and **Heart Rate Variability (HRV)** from that signal, entirely on-device, with no wearables, no external sensors, and no data ever leaving the phone.

Every result is paired with a plain-language explanation of what drove it, rather than a black-box number, using SHAP-based feature attribution.

<div align="center">

<!-- Screenshot/demo placeholder -->
<!-- <img src="docs/assets/demo.gif" alt="CardioLens demo" width="280"/> -->
*Demo screenshots and GIF coming soon*

</div>

---

## Table of Contents

- [How It Works](#how-it-works)
- [Key Design Principles](#key-design-principles)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Dataset & Evaluation Methodology](#dataset--evaluation-methodology)
- [Getting Started](#getting-started)
- [Project Status](#project-status)
- [Roadmap](#roadmap)
- [Reference Literature](#reference-literature)
- [Limitations](#limitations)
- [Contributing](#contributing)
- [License](#license)

---

## How It Works

CardioLens processes a live facial video feed in four stages:

### 1. Sensing
The app captures live facial video at 30-60 fps using Android's CameraX API. On-device face detection (ML Kit) identifies a stable region of interest (forehead/cheeks), and the app extracts the average RGB pixel values from that region on every frame, forming a raw time-series signal.

### 2. Signal Processing
The raw RGB signal is processed using chrominance-based extraction (CHROM/POS algorithms) to isolate the pulse-related color change from ambient noise, followed by a Butterworth bandpass filter (0.7-4.0 Hz) to remove lighting fluctuations and motion artifacts. The output is a clean pulse waveform.

### 3. Inference
A lightweight 1D-CNN / quantized MobileNet model, trained on the public UBFC-rPPG dataset and converted to TensorFlow Lite, estimates Heart Rate, HRV, and a derived stress indicator from the cleaned waveform, running entirely on-device with sub-second latency.

### 4. Explainability
SHAP (SHapley Additive exPlanations) identifies which signal characteristics most influenced a given result. These feature attributions are converted into a short, human-readable explanation, so a result is never just a number without reasoning behind it.

```
Face Video (CameraX) -> ROI Extraction (ML Kit) -> RGB Signal
     -> CHROM/POS + Bandpass Filter -> Clean Pulse Waveform
     -> TFLite Model (HR / HRV / Stress) -> SHAP Explainability
     -> On-screen Result + Plain-language Explanation
```

---

## Key Design Principles

- **No external hardware required** — works with any standard Android smartphone camera.
- **Fully on-device inference** — raw video is never uploaded or stored externally; only derived numeric metrics are used for any external processing step.
- **Explainable by design** — every prediction includes a feature-level explanation, not just a risk score.
- **Not a diagnostic device** — CardioLens is a screening aid intended to surface early indicators for further professional evaluation, not a certified medical device.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Mobile app | Kotlin, Jetpack Compose, Android CameraX API |
| Face detection | ML Kit Face Detection |
| On-device inference | TensorFlow Lite (quantized `.tflite`) |
| Model training | Python, PyTorch |
| Signal processing | NumPy, SciPy (Butterworth filtering), CHROM/POS algorithms |
| Explainability | SHAP |
| Dataset | UBFC-rPPG (public research dataset) |

---

## Project Structure

```
CardioLens/
├── app/                     # Kotlin + Jetpack Compose Android application
├── python-training/         # Model training scripts and notebooks
│   └── ubfc_evaluation.py      # Ground-truth loading & MAE evaluation utilities
├── dataset/                 # Local UBFC-rPPG dataset (not committed to version control)
├── docs/                    # Architecture notes, references, design docs, assets
├── results/                 # Evaluation outputs, logs, test recordings
├── LICENSE
└── README.md
```

---

## Dataset & Evaluation Methodology

CardioLens is trained and validated on the [UBFC-rPPG dataset](https://sites.google.com/view/ybenezeth/ubfcrppg), a public dataset of facial videos synchronized with ground-truth contact PPG sensor data.

> **Ground-truth rule:** Per the dataset's official documentation, the pre-computed Heart Rate column supplied with the dataset is **not** used as the evaluation baseline. Instead, ground-truth HR is derived by peak-detecting the raw contact PPG waveform directly, using the same method applied to the camera-derived (remote) PPG signal. This ensures a true apples-to-apples comparison between remote and contact PPG, consistent with how the dataset's original authors evaluated their own method.

Accuracy is reported as **Mean Absolute Error (MAE)** in beats per minute (BPM) between the remote (camera-based) HR estimate and the contact (oximeter-derived) HR estimate.

Validation is performed in two stages:

1. **Dataset-level validation** — model performance measured against UBFC-rPPG's raw contact PPG wave.
2. **Live validation** — the deployed app's real-time HR reading compared against a physical pulse oximeter across multiple lighting conditions, to evaluate real-world performance beyond the training dataset.

---

## Getting Started

### Prerequisites
- Android Studio (latest stable release)
- Python 3.10+
- Access to the UBFC-rPPG dataset (request via the official dataset page)

### Setup

```bash
# Clone the repository
git clone <repository-url>
cd CardioLens

# Set up the Python environment
python -m venv venv
source venv/bin/activate      # On Windows: venv\Scripts\activate
pip install numpy scipy opencv-python torch shap

# Open app/ in Android Studio and sync Gradle
```

---

## Project Status

**Phase 1 — In Progress**

- [x] Initial project architecture setup
- [x] CameraX and ML Kit dependencies integrated
- [ ] Forehead ROI extraction logic *(next)*
- [ ] Raw signal time-series logging *(next)*
- [ ] CHROM/POS signal cleaning pipeline
- [ ] Model training on UBFC-rPPG
- [ ] TensorFlow Lite on-device deployment
- [ ] SHAP explainability layer
- [ ] Live pulse-oximeter validation

---

## Roadmap

- [ ] Complete live camera-to-waveform pipeline
- [ ] Train and validate baseline 1D-CNN model
- [ ] Ship on-device TFLite inference with sub-second latency
- [ ] Integrate SHAP-based explainability dashboard
- [ ] Validate against pulse oximeter across varied lighting conditions
- [ ] Publish evaluation results and demo recordings

---

## Reference Literature

CardioLens builds on established and current research in camera-based physiological signal estimation:

1. *A Flexible Framework for Design and Validation of rPPG Methods*, IEEE Access, vol. 13, 2025.
2. S. G. Ahmed et al., *AI Innovations in rPPG Systems for Driver Monitoring: Comprehensive Systematic Review and Future Prospects*, IEEE Access, vol. 13, pp. 22893-22918, 2025.
3. *A Comprehensive Review of Deepfake Detection Techniques Utilizing Remote Photoplethysmography*, IEEE Journals & Magazine, IEEE Xplore, 2025.

---

## Limitations

Camera-based rPPG accuracy is known to be sensitive to lighting conditions, motion, skin tone, and camera quality, this is a documented characteristic of the field, not unique to this implementation. CardioLens reports its measured accuracy transparently across tested conditions rather than claiming clinical-grade precision. It is intended as an accessible screening aid, not a replacement for validated medical devices.

---

## Contributing

This project is under active development. Issues, suggestions, and pull requests are welcome, please open an issue to discuss any significant change before submitting a PR.

---

## License

This project is licensed under the **Apache License 2.0** — see the [LICENSE](LICENSE) file for details.