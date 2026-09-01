# RootPreflight

**Know what a root module will do before you install it.**

RootPreflight is an Android pre-install safety and compatibility scanner for **Magisk, KernelSU and APatch-style modules**.

Choose a module ZIP and RootPreflight inspects its metadata, boot/install scripts, system overlays, property changes and archive structure before you install it.

> **Status:** `0.1.0-alpha.1` MVP. The static scanner and optional root-aware conflict index are implemented.

## Why this exists

Root modules can execute scripts during installation and boot, override system files and properties, start long-running services, alter mounts and in some cases touch boot-critical storage. Reading every shell script manually is slow, and two individually valid modules can still conflict with each other.

RootPreflight turns that review into an understandable report:

```text
Module: Example Performance Module
Risk: HIGH (67/100)

HIGH    Direct block-device write
MEDIUM  post-fs-data.sh execution hook
MEDIUM  Runtime property override
LOW     14 system overlay targets

Installed-module conflicts:
- 2 overlapping /system targets with ExampleCore
- 1 overlapping property key with TunerModule
```

## MVP capabilities

- Scan a module ZIP without extracting it to storage
- Parse `module.prop`
- Recognize common lifecycle/install scripts
- Detect high-impact shell patterns such as direct block-device writes, filesystem formatting, broad destructive deletes, SELinux weakening and network-fetched shell execution
- Detect mount/bind/overlay activity, property overrides, package control, firewall changes and broad writable permissions
- Index `system/` overlay targets
- Index `system.prop` keys
- Flag native-looking payloads that need deeper review
- Detect absolute paths, parent traversal and duplicate ZIP entries
- Apply entry/text/uncompressed-data limits to reduce ZIP-bomb exposure
- Work in **Static Mode** on an unrooted phone
- If root is granted, index installed modules under `/data/adb/modules` and report overlapping file/property targets

## Static Mode vs Root Mode

**Static Mode** does not require root. It answers: *What is inside this ZIP and what behaviors are visible before installation?*

**Root Mode** adds device context. It can inspect installed module overlays/properties and answer: *Will this ZIP collide with something already installed here?*

RootPreflight does **not** install or execute a selected module in the MVP.

## Risk score

The score is a deterministic heuristic, not an AI verdict. High-impact patterns add more weight than ordinary lifecycle hooks or overlay files. The score is intentionally explainable: every point comes from visible findings in the report.

- `LOW`: 0–19
- `MEDIUM`: 20–49
- `HIGH`: 50–100

A LOW score is **not a guarantee of safety**. Static analysis cannot fully determine the behavior of native binaries, obfuscated code, runtime downloads or device/OEM-specific interactions.

## Project structure

```text
app/            Android UI + optional root environment probe
scanner-core/   Pure Kotlin ZIP/module analysis engine + conflict detector
.github/        CI
```

The scanner engine is kept separate from Android UI code so its rules can be unit-tested and later reused by CLI/CI integrations.

## Android baseline

- `compileSdk = 37`
- `targetSdk = 37`
- `minSdk = 26`
- Android Gradle Plugin `9.3.0`
- AGP 9 built-in Kotlin (Compose compiler plugin `2.3.21`)
- JDK `17`
- Compose BOM `2026.08.00`

## Build

With Gradle 9.5.0 available:

```bash
gradle :scanner-core:test :app:assembleDebug
```

GitHub Actions installs the required toolchain and builds the debug APK automatically.

## Security model

RootPreflight treats selected ZIPs as untrusted input. The scanner does not execute module scripts and does not extract the selected module into system locations. Root access, when granted, is used only for read-only indexing of the installed module workspace in the current MVP.

## Non-goals

RootPreflight is not an antivirus engine, anti-cheat bypass tool, root-hiding tool or guarantee that a module cannot bootloop a specific device. Its job is explainable **pre-install inspection and compatibility analysis**.

## License

MIT — see [`LICENSE`](LICENSE).
