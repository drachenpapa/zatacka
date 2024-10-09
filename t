[1mdiff --git a/.github/workflows/build-and-test.yml b/.github/workflows/build-and-test.yml[m
[1mindex 236bb16..25b382c 100644[m
[1m--- a/.github/workflows/build-and-test.yml[m
[1m+++ b/.github/workflows/build-and-test.yml[m
[36m@@ -10,7 +10,6 @@[m [mjobs:[m
   build:[m
     name: Build and Test[m
     runs-on: ubuntu-latest[m
[31m-[m
     steps:[m
       - name: Checkout[m
         uses: actions/checkout@v4[m
[36m@@ -46,50 +45,3 @@[m [mjobs:[m
           path: target/surefire-reports/*.xml[m
           reporter: java-junit[m
           fail-on-error: true[m
[31m-[m
[31m-  codeql:[m
[31m-    name: Analyze with CodeQL[m
[31m-    needs: build[m
[31m-    runs-on: 'ubuntu-latest'[m
[31m-    timeout-minutes: 360[m
[31m-    strategy:[m
[31m-      fail-fast: false[m
[31m-    steps:[m
[31m-      - name: Checkout[m
[31m-        uses: actions/checkout@v4[m
[31m-      - name: Set up JDK[m
[31m-        uses: actions/setup-java@v4[m
[31m-        with:[m
[31m-          java-version: '17'[m
[31m-          distribution: 'temurin'[m
[31m-          cache: maven[m
[31m-      - name: Initialize CodeQL[m
[31m-        uses: github/codeql-action/init@v3[m
[31m-        with:[m
[31m-          languages: java-kotlin[m
[31m-      - name: Build[m
[31m-        run: mvn -B package -DskipTests[m
[31m-      - name: Perform CodeQL analysis[m
[31m-        uses: github/codeql-action/analyze@v3[m
[31m-        with:[m
[31m-          category: "/language:java-kotlin"[m
[31m-[m
[31m-[m
[31m-  snyk-scan:[m
[31m-    name: Snyk Security Scan[m
[31m-    runs-on: ubuntu-latest[m
[31m-    steps:[m
[31m-      - name: Checkout[m
[31m-        uses: actions/checkout@v4[m
[31m-      - name: Set up JDK[m
[31m-        uses: actions/setup-java@v4[m
[31m-        with:[m
[31m-          java-version: '17'[m
[31m-          distribution: 'temurin'[m
[31m-          cache: maven[m
[31m-      - name: Build[m
[31m-        run: mvn -B package -DskipTests[m
[31m-      - name: Snyk Security Scan[m
[31m-        uses: snyk/actions/maven@master[m
[31m-        env:[m
[31m-          SNYK_TOKEN: ${{ secrets.SNYK_TOKEN }}[m
[1mdiff --git a/.github/workflows/snyk-security.yml b/.github/workflows/snyk-security.yml[m
[1mindex e69de29..230c83e 100644[m
[1m--- a/.github/workflows/snyk-security.yml[m
[1m+++ b/.github/workflows/snyk-security.yml[m
[36m@@ -0,0 +1,31 @@[m
[32m+[m[32mname: Snyk Security[m[41m[m
[32m+[m[41m[m
[32m+[m[32mon:[m[41m[m
[32m+[m[32m  push:[m[41m[m
[32m+[m[32m    branches: ["main"][m[41m[m
[32m+[m[32m  pull_request:[m[41m[m
[32m+[m[32m    branches: ["main"][m[41m[m
[32m+[m[41m[m
[32m+[m[32mjobs:[m[41m[m
[32m+[m[32m  snyk:[m[41m[m
[32m+[m[32m    name: Run Snyk Security[m[41m[m
[32m+[m[32m    permissions:[m[41m[m
[32m+[m[32m      contents: read[m[41m[m
[32m+[m[32m      security-events: write[m[41m[m
[32m+[m[32m      actions: read[m[41m[m
[32m+[m[32m    runs-on: ubuntu-latest[m[41m[m
[32m+[m[32m    steps:[m[41m[m
[32m+[m[32m      - name: Checkout repository[m[41m[m
[32m+[m[32m        uses: actions/checkout@v4[m[41m[m
[32m+[m[32m      - name: Run Snyk Code[m[41m[m
[32m+[m[32m        uses: snyk/actions/maven@master[m[41m[m
[32m+[m[32m        continue-on-error: true[m[41m[m
[32m+[m[32m        env:[m[41m[m
[32m+[m[32m          SNYK_TOKEN: ${{ secrets.SNYK_TOKEN }}[m[41m[m
[32m+[m[32m        with:[m[41m[m
[32m+[m[32m          command: code test[m[41m[m
[32m+[m[32m          args: --sarif-file-output=snyk.sarif[m[41m[m
[32m+[m[32m      - name: Upload results[m[41m[m
[32m+[m[32m        uses: github/codeql-action/upload-sarif@v3[m[41m[m
[32m+[m[32m        with:[m[41m[m
[32m+[m[32m          sarif_file: snyk.sarif[m[41m[m
[1mdiff --git a/.github/workflows/validate-pr.yml b/.github/workflows/validate-pr.yml[m
[1mindex a24387d..2f1c382 100644[m
[1m--- a/.github/workflows/validate-pr.yml[m
[1m+++ b/.github/workflows/validate-pr.yml[m
[36m@@ -6,11 +6,9 @@[m [mon:[m
       - 'renovate/*'[m
     types: [opened, edited, reopened, synchronize][m
 [m
[31m-[m
 permissions:[m
   pull-requests: read[m
 [m
[31m-[m
 jobs:[m
   lint-pr-title:[m
     name: Lint pull request title[m
[36m@@ -32,54 +30,3 @@[m [mjobs:[m
             revert[m
             test[m
           requireScope: false[m
[31m-[m
[31m-[m
[31m-  codeql-analyzis:[m
[31m-    name: Analyze with CodeQL[m
[31m-    runs-on: ubuntu-latest[m
[31m-    steps:[m
[31m-      - name: Checkout repository[m
[31m-        uses: actions/checkout@v4[m
[31m-[m
[31m-      - name: Set up JDK[m
[31m-        uses: actions/setup-java@v4[m
[31m-        with:[m
[31m-          java-version: '17'[m
[31m-          distribution: 'temurin'[m
[31m-          cache: maven[m
[31m-[m
[31m-      - name: Initialize CodeQL[m
[31m-        uses: github/codeql-action/init@v3[m
[31m-        with:[m
[31m-          languages: java-kotlin[m
[31m-[m
[31m-      - name: Build[m
[31m-        run: mvn -B package -DskipTests[m
[31m-[m
[31m-      - name: Perform CodeQL analysis[m
[31m-        uses: github/codeql-action/analyze@v3[m
[31m-        with:[m
[31m-          category: "/language:java-kotlin"[m
[31m-[m
[31m-[m
[31m-  snyk-scan:[m
[31m-    name: Snyk Security Scan[m
[31m-    runs-on: ubuntu-latest[m
[31m-    steps:[m
[31m-      - name: Checkout repository[m
[31m-        uses: actions/checkout@v4[m
[31m-[m
[31m-      - name: Set up JDK[m
[31m-        uses: actions/setup-java@v4[m
[31m-        with:[m
[31m-          java-version: '17'[m
[31m-          distribution: 'temurin'[m
[31m-          cache: maven[m
[31m-[m
[31m-      - name: Build[m
[31m-        run: mvn -B package -DskipTests[m
[31m-[m
[31m-      - name: Snyk Security Scan[m
[31m-        uses: snyk/actions/maven@master[m
[31m-        env:[m
[31m-          SNYK_TOKEN: ${{ secrets.SNYK_TOKEN }}[m
