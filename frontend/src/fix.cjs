const fs = require('fs');
let c = fs.readFileSync('App.jsx', 'utf8');

// First, fix the broken ones from the previous PowerShell command
c = c.replace(/fetch\(apiUrl\('(\/api\/[^']+)', \{/g, "fetch('/api/$1', {");
c = c.replace(/fetch\(apiUrl\('(\/api\/[^']+)'\n/g, "fetch('/api/$1'\n");

// Now do the proper replacement
c = c.replace(/fetch\('(\/api\/[^']+)'/g, "fetch(apiUrl('$1')");
c = c.replace(/fetch\(`(\/api\/[^`]+)`/g, "fetch(apiUrl(`$1`)");

fs.writeFileSync('App.jsx', c);
