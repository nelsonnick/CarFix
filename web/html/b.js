var glob = require('glob');
function getMultiEntry(globPath) {
  var files = glob.sync(globPath),
    entries = {};

  files.forEach(function(filepath) {
    var split = filepath.split('/');
    var name = split[split.length-1].substring(0,split[split.length-1].length-5);
    var entity = [];
    entity.push(filepath);
    entries[name] = entity;
  });

  return entries;
}
var pages = getMultiEntry('../dist/static/html/mobile/**.html');
console.log('1111')
for (var pathname in pages) {
  console.log(pathname)
  var conf = {
    filename: '../dist/static/html/mobile/' + pathname + '.html',
    template: 'html/template.html', // 模板路径
    chunks: ['vendor', 'manifest', 'mobile_' + pathname],
    inject: true,
    hash: true,
    minify: {
      removeComments: true,
      collapseWhitespace: true,
      removeAttributeQuotes: true
      // more options:
      // https://github.com/kangax/html-minifier#options-quick-reference
    },
    // necessary to consistently work with multiple chunks via CommonsChunkPlugin
    chunksSortMode: 'dependency'
  };
  console.log(conf)
}
