# Pull Request へ報告しないように設定
github.dismiss_out_of_range_messages

# Pull Requestのタイトルに [WIP] が入っているか警告
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

# ktlint
checkstyle_format.base_path = Dir.pwd
checkstyle_format.report "app/build/reports/ktlint/ktlintKotlinScriptCheck.xml"
