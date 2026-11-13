# Deployment

## Debug build
```bash
./gradlew assembleDebug
```

The debug APK is generated under `app/build/outputs/apk/debug/`.

## Release preparation
1. Confirm unit and instrumentation tests pass.
2. Review app label, icon, version code, and version name.
3. Review backup behavior and privacy disclosures.
4. Configure release signing through secure CI secrets or a local protected keystore.
5. Never commit signing keys or passwords.
6. Build and test a signed release APK or Android App Bundle.
7. Test install, upgrade, note retention, and migration behavior on supported Android versions.

Do not publish a release until data backup and privacy behavior are understood.
