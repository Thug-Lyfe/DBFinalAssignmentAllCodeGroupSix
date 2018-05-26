const fs = require('fs');




function fix(prefix, file) {
    fs.readFile("./" + file, 'utf-8', function (err, content) {
        if (err) {
            console.log(err)
            return;
        }
        content.split("\n").forEach((line) => {
            let line_split = line.split(",");
            fs.appendFileSync('./neo4j' + file.substring(file.indexOf("_")), "b"+line_split[0]+",c"+line_split[1]+ '\n', 'utf8', function (err) {
                if (err) {
                    console.log('Some error occured - file either not saved or corrupted file saved.');
                    console.log(err)
                } else {
                    //console.log('psql_book.csv is saved!');
                }
            });

        })
    })
}
function fix_books() {
    fs.readFile('./psql_book.csv', 'utf-8', function (err, content) {
        if (err) {
            console.log(err)
            return;
        }
        content.split("\n").forEach((line) => {
            let last_ind = line.indexOf(",", line.indexOf(",", line.indexOf(',') + 1) + 1) + 1
            let firstpart = line.substring(0, last_ind)
            let lastpart = line.substring(line.lastIndexOf(","))

            let middle = "\"" + line.substring(last_ind + 1, line.lastIndexOf(",") - 1).split("\"").join("\'") + "\"";
            fs.appendFileSync('./psql_book_fix.csv', firstpart + middle + lastpart + '\n', 'utf8', function (err) {
                if (err) {
                    console.log('Some error occured - file either not saved or corrupted file saved.');
                    console.log(err)
                } else {
                    //console.log('psql_book.csv is saved!');
                }
            });

        })
    })
}
fix("c","psql_mention.csv")