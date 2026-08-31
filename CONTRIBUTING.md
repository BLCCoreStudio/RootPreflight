# Contributing

Contributions are welcome, especially analyzer rules backed by real module behavior and reproducible test fixtures.

For a new detection rule:

1. Keep it explainable and narrowly scoped.
2. Avoid treating ordinary root-framework behavior as malicious by default.
3. Add a unit test for both detection and a reasonable non-match when possible.
4. Describe whether the finding is about safety, compatibility or simply review visibility.
5. Prefer reporting observable behavior over guessing author intent.

False-positive reductions are as valuable as new detections.
