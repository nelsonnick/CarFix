var glob = require('glob');
function getEntries(globPath) {
  var files = glob.sync(globPath),
    entries = {};

  files.forEach(function (filepath) {
    var split = filepath.split('/');
    var name = split[split.length - 2];
    console.log(name)
    var entity = [];
    entity.push(filepath);
    entries['mobile_'+name] = entity;
  });

  return entries;
}
var pages = getEntries('./src/mobile/**/index.js')
console.log(pages)
for (var pathname in pages) {
  console.log(pathname)
}
