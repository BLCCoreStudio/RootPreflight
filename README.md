# RootPreflight

> **Status: incomplete research prototype — not a usable Android application.**

RootPreflight explored the idea of inspecting Magisk, KernelSU and APatch-style module metadata before installation. The repository currently contains only an early Kotlin core model and conflict-detection foundation.

It does **not** currently ship a complete ZIP scanner, Android UI, APK, release artifact, or end-to-end module analysis workflow.

## What exists

The current `scanner-core` module contains early Kotlin types and conflict-detection logic intended to support future static analysis work.

Current repository structure:

```text
scanner-core/   Early Kotlin analysis model and conflict-detection foundation
```

The Gradle project intentionally includes only the code that is actually present in the repository.

## Original research direction

The prototype was intended to investigate whether a local, explainable pre-install inspection tool could eventually:

- parse root-module metadata,
- inspect lifecycle/install scripts without executing them,
- identify potentially destructive shell patterns,
- compare module overlay/property targets,
- detect obvious archive-path hazards,
- and explain possible conflicts with installed modules.

Those capabilities should be treated as **design goals, not implemented product claims**.

## Safety boundary

Any future implementation should treat selected module archives as untrusted input and avoid executing their scripts during static inspection. Static analysis alone also cannot prove that a module is safe, particularly when native binaries, obfuscated payloads, runtime downloads, or device-specific behavior are involved.

## Development status

This repository is not currently one of BLCCoreStudio's actively developed flagship projects. It is retained as an early research snapshot unless the concept is resumed with a complete implementation and verifiable tests.

## License

MIT — see [`LICENSE`](LICENSE).
