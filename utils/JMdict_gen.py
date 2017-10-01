#!/usr/bin/env python

max_freq = 0

entries = []

class Entry:
    def __init__(self, seq):
        self.seq = seq
        self.symbols = []
        self.meanings = []
        self.readings = []
        self.freq = None

    def print(self):
        if self.freq is None or len(self.symbols) == 0 or len(self.meanings) == 0:
            return

        print("E")
        for s in self.symbols:
            print("S:" + s)
        for m in self.meanings:
            print("T:" + m)
        for m in self.readings:
            print("R:" + m)

def parse_entry(ele):
    global max_freq
    global entries
    seq = ele.find('ent_seq').text
    e = Entry(seq)
    for child in ele:
        if child.tag == "k_ele":
            for child2 in child:
                if child2.tag == "keb":
                    e.symbols.append(child2.text)
                if child2.tag == "ke_pri":
                    t = child2.text
                    if t.startswith("nf") and e.freq is None:
                        e.freq = int(t[2:])
        elif child.tag == "r_ele":
            for child2 in child:
                if child2.tag == "reb":
                    e.readings.append(child2.text)
        elif child.tag == "sense":
            for child2 in child:
                if child2.tag == "gloss":
                    e.meanings.append(child2.text)
    if e.freq:
        if e.freq > max_freq:
            max_freq = e.freq
        entries.append(e)


import xml.etree.ElementTree as ET
tree = ET.parse('JMdict_e')
root = tree.getroot()
for entry in root:
    if entry.tag == "entry":
        parse_entry(entry)

entries.sort(key=lambda x: x.freq, reverse=False)

e_printed = 0
for e in entries:
    e.print()
    e_printed+=1

#print(max_freq)
