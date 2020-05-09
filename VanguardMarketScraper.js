// ==UserScript==
// @name         Vanguard Market Scraper
// @namespace    https://www.joehxblog.com
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        https://investor.vanguard.com/mutual-funds/profile/overview/*/portfolio-holdings
// @grant        none
// ==/UserScript==

(function() {
    'use strict';

    let component =
    `<div id="hj-div" style="position: fixed; top: 100px; left: 10px;">
     <button id="hj-scrape" style="display: block;padding: 0.5em;height: inherit;">Scrape</button>
     </div>`

    $("body").append(component);

    $("#hj-scrape").click(() => {
        let array = [];

        let nextButton = "a[data-ng-click='goToNextPage()']";
        let dataRow = "table.scrollingTableLeft tr td.fixedCol";

        let read = (index, value) => {
                let text = $(value).text().trim();

                array.push(text);
            };

        while(!$(nextButton).hasClass("inActiveLink")) {
            $(dataRow).slice(0,30).each(read);
            $(nextButton).first().click();
        }

        $(dataRow).slice(0,30).each(read);

        let filecontents = array.reduce((total, item) => total + item + "\n");
        let filename = window.location.pathname.replace("/mutual-funds/profile/overview/", "").replace("/portfolio-holdings", "") + ".txt";

        let givemeabreak = document.createElement('br');
        let hiddenElement = document.createElement('a');
        hiddenElement.href = 'data:text/csv;charset=utf-8,' + encodeURI(filecontents);
        hiddenElement.target = '_blank';
        hiddenElement.download = filename;
        hiddenElement.textContent = filename;
        $("#hj-div").append(hiddenElement);
        $("#hj-div").append(givemeabreak);

        console.log(array);
        console.log(filecontents);
    });
})();