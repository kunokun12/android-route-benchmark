# Android Route Benchmark — Phase 7.6A

Deterministic, offline Android app for benchmarking guarded three-step navigation with Android MCP. No network permission, accounts, forms, or settings changes.

## Navigation

1. **Benchmark Home** — exact button `Open Guide` → **Benchmark Guide**; destination marker `Guide Screen Verified`
2. **Benchmark Guide** — exact button `View Details` → **Benchmark Details**; destination marker `Details Screen Verified`
3. **Benchmark Details** — exact button `About Test` → **Benchmark About**; destination marker `About Screen Verified`

The labels are source-code expectations, **not evidence of actual on-device accessibility snapshots**. Inspect each destination on the device before recording a learned route.

## APK via GitHub Actions

Open **Actions → Build Android debug APK**, run the workflow (or push to main), and download the `android-route-benchmark-debug-apk` artifact from the successful run. Extract `app-debug.apk`, then install:

```bash
adb install -r app-debug.apk
adb shell am start -n dev.androidroute.benchmark/.MainActivity -a dev.androidroute.benchmark.RESET
```

Package: `dev.androidroute.benchmark`. Android Back moves one screen backward. RESET returns to Home.

## Local build

Requires JDK 17, Android SDK platform 35 and Gradle 8.9:

```bash
gradle :app:assembleDebug
```

There is no Gradle wrapper in this repository.

## Benchmark safety

Start one Android MCP session and record original browser package/activity/URL. Use fresh accessibility snapshots to confirm the exact target and destination markers at each step. Capture all three steps only after independent observation; require unique enabled visible targets, matching package, destination markers absent on source, and verified destination markers after navigation. Return to Home, assess, dry-run, and only then replay. Never retry uncertain actions or tap guessed coordinates. Finish and independently verify original browser restoration.

For a meaningful A/B benchmark, compare three separate guarded semantic actions versus one three-step local replay, with equivalent verification and independent host-side wall-clock timestamps. MCP-local timing alone cannot prove Codex end-to-end speedup.

**Status:** Source uploaded; GitHub Actions build and real-device accessibility verification are not yet confirmed.
