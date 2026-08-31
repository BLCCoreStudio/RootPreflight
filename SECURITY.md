# Security

RootPreflight processes untrusted root-module archives. Security bugs that allow a selected archive to escape scan boundaries, execute content unexpectedly, exhaust resources beyond configured limits, or misuse granted root access are considered high priority.

Please avoid publishing a working exploit before a fix is available. Open a minimal report with reproduction details and clearly mark it as a security issue; if private reporting is enabled for this repository, prefer that channel.

The current MVP never installs or executes the selected module. Root access is optional and used only to read installed module metadata/targets for conflict analysis.
