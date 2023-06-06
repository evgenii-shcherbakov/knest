#!/usr/bin/env bash

KEYSTORE_HOST=${KEYSTORE_HOST:-$1}
KEYSTORE_ACCESS_TOKEN=${KEYSTORE_ACCESS_TOKEN:-$2}

ORIGINAL_PROPERTIES=""
PUBLISH_PROPERTIES=""

setup_git() {
  echo "Setup git..."
  git config user.name "GitHub Action"
  git config user.email "action@github.com"
  git config pull.rebase true
}

load_publish_properties() {
  echo "Load gradle properties for publishing..."
  PUBLISH_PROPERTIES=$(
    curl \
      -X "POST" \
      -H "Authorization: Bearer $KEYSTORE_ACCESS_TOKEN" \
      -d "{\"format\":\"gradle-properties\"}" \
      --url "$KEYSTORE_HOST/applications/knest/publishing/maven-central"
  )
}

add_publish_properties() {
  echo "Add publish gradle properties..."
  ORIGINAL_PROPERTIES="$(<gradle.properties)"
  echo "" >> gradle.properties || exit 1
  echo "$PUBLISH_PROPERTIES" >> gradle.properties || exit 1
}

restore_original_properties() {
  echo "Restore original gradle properties..."
  echo "$ORIGINAL_PROPERTIES" > gradle.properties || exit 1
}

update_version() {
  local VERSION_NAME_SEARCH_PATTERN="VERSION_NAME="
  local VERSION_NAME_READ_PATTERN="/$VERSION_NAME_SEARCH_PATTERN/s/.*=\\([^=]*\\).*/\\1/p"
  local BUILD_NUMBER_SEARCH_PATTERN="BUILD_NUMBER="
  local BUILD_NUMBER_READ_PATTERN="/$BUILD_NUMBER_SEARCH_PATTERN/s/.*=\\([^=]*\\).*/\\1/p"
  local FILE="framework/gradle.properties"
  local NEW_VALUE
  local PARTS

  echo "Patch version..."

  if git diff HEAD~ HEAD --unified=0 -- "$FILE" | grep -q "+.*$VERSION_NAME_SEARCH_PATTERN.*"
    then
      echo "Parameter 'VERSION_NAME' in $FILE already updated, skip auto-patching..."
    else
      IFS='.' read -ra PARTS <<< "$(printf "%s\n" "$VERSION_NAME_READ_PATTERN" "q" | ed -s "$FILE")"
      NEW_VALUE="${PARTS[0]}.${PARTS[1]}.$((${PARTS[2]}+1))"
      printf "%s\n" "H" "/$VERSION_NAME_SEARCH_PATTERN.*" "s//VERSION_NAME=$NEW_VALUE/" "wq" | ed -s "$FILE"
  fi

  if git diff HEAD~ HEAD --unified=0 -- "$FILE" | grep -q "+.*$BUILD_NUMBER_SEARCH_PATTERN.*"
    then
      echo "Parameter 'BUILD_NUMBER' in $FILE already updated, skip auto-patching..."
    else
      IFS='.' read -ra PARTS <<< "$(printf "%s\n" "$BUILD_NUMBER_READ_PATTERN" "q" | ed -s "$FILE")"
      NEW_VALUE=$((${PARTS[0]}+1))
      printf "%s\n" "H" "/$BUILD_NUMBER_SEARCH_PATTERN.*" "s//BUILD_NUMBER=$NEW_VALUE/" "wq" | ed -s "$FILE"
  fi
}

publish() {
  echo "Publish..."
  ./gradlew publishAllPublicationsToMavenCentralRepository --no-configuration-cache || exit 1
}

update_git_branch() {
  echo 'Update main branch...'
  git add .
  git commit -m "Update version"
  git pull origin main
  git push origin main
}

main() {
#  setup_git
  chmod +x ./gradlew || exit 1
  update_version
#  load_publish_properties
#  add_publish_properties
#  publish
#  restore_original_properties
#  update_git_branch
}

main
