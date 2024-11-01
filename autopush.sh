git add .
git commit -m "default message"
# shellcheck disable=SC2046
git push origin -u $(git branch --show-current)