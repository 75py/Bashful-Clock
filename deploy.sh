echo "deploy.sh begin"

gem install fastlane --no-rdoc --no-ri --no-document --quiet
fastlane deploy_alpha

echo "deploy.sh end"
